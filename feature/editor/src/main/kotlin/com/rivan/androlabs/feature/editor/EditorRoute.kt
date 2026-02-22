/*
 * Copyright 2023 Rivan Parmar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.feature.editor

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rivan.androlabs.core.data.repository.ProjectViewNode
import com.rivan.androlabs.core.designsystem.component.ALScaffold
import com.rivan.androlabs.core.designsystem.component.ALTopAppBar
import kotlinx.coroutines.launch

@Composable
internal fun EditorRoute(
    viewModel: EditorViewModel = hiltViewModel(),
) {
    val visibleNodes by viewModel.visibleNodes.collectAsStateWithLifecycle()

    EditorScreen(
        visibleNodes = visibleNodes,
        loadProjectView = viewModel::loadProjectView,
        toggleFolder = viewModel::toggleFolder,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun EditorScreen(
    visibleNodes: List<ProjectViewNode>,
    loadProjectView: (uri: Uri) -> Unit,
    toggleFolder: (node: ProjectViewNode) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val options = listOf("Project", "Packages", "Android")
    var expanded by remember { mutableStateOf(false) }
    var checkedIndex: Int? by remember { mutableStateOf(null) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(modifier = Modifier.wrapContentSize()) {
                        OutlinedButton(
                            onClick = { expanded = !expanded },
                            shape = ButtonDefaults.squareShape
                        ) {
                            Row(horizontalArrangement = Arrangement.Center) {
                                val rotation: Float by
                                animateFloatAsState(
                                    targetValue = if (expanded) 180f else 0f,
                                    label = "Trailing Icon Rotation",
                                )
                                Text(
                                    text = options[checkedIndex ?: 0],
                                    maxLines = 1,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    Icons.Filled.KeyboardArrowDown,
                                    modifier =
                                        Modifier
                                            .size(SplitButtonDefaults.TrailingIconSize)
                                            .graphicsLayer {
                                                this.rotationZ = rotation
                                            },
                                    contentDescription = null,
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            shape = MenuDefaults.standaloneGroupShape,
                        ) {
                            options.forEachIndexed { index, option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = { checkedIndex = index },
                                )
                            }
                        }
                    }

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = visibleNodes,
                        key = { it.documentId },
                    ) { node ->
                        ProjectViewNodeItem(
                            projectViewNode = node,
                            onClick = {
                                if (node.isDirectory) {
                                    toggleFolder(node)
                                } else {
                                    // TODO
                                }
                            },
                        )
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true,
    ) {
        ALScaffold(
            topAppBar = {
                ALTopAppBar(
                    title = "Android app",
                    navigationIcon = Icons.Default.Menu,
                    navigationIconContentDescription = null,
                    onNavigationClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            },
        ) {

        }
    }
}