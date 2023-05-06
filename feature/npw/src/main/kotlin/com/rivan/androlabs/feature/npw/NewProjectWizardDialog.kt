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

package com.rivan.androlabs.feature.npw

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rivan.androlabs.core.designsystem.component.ALScrollableTabRow
import com.rivan.androlabs.core.designsystem.component.ALTab
import com.rivan.androlabs.core.designsystem.component.ALTabRow
import com.rivan.androlabs.wizard.template.api.Template

@Composable
fun NewProjectWizardDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    isSmallScreen: Boolean,
    npwViewModel: NewProjectWizardViewModel = viewModel()
) {
    val tabState by npwViewModel.tabState.collectAsStateWithLifecycle()

    NewProjectWizardDialog(
        modifier = modifier,
        onDismiss = onDismiss,
        tabState = tabState,
        shouldUseScrollableTabRow = isSmallScreen,
        switchTab = npwViewModel::switchTab,
        templates = npwViewModel.getTemplates()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun NewProjectWizardDialog(
    onDismiss: () -> Unit,
    tabState: NpwTabState,
    switchTab: (Int) -> Unit,
    shouldUseScrollableTabRow: Boolean,
    templates: List<Template>,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableStateOf(-1) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            // Set platform default width to false, which allows us to create a full-screen dialog.
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true
        )
    ) {
        Scaffold(
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.templates)) },
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = { /*TODO*/ },
                            // Enable this only when the user has selected a template
                            enabled = selectedIndex != -1
                        ) {
                            Text(text = "Next")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NewProjectWizardContent(
                    tabState = tabState,
                    switchTab = switchTab,
                    shouldUseScrollableTabRow = shouldUseScrollableTabRow,
                    templates = templates,
                    onTemplateClick = { index ->
                        selectedIndex = if (selectedIndex != index) index else -1
                    },
                    selectedIndex = selectedIndex
                )
            }
        }
    }
}

@Composable
private fun NewProjectWizardContent(
    tabState: NpwTabState,
    switchTab: (Int) -> Unit,
    templates: List<Template>,
    onTemplateClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedIndex: Int = -1,
    shouldUseScrollableTabRow: Boolean = true
) {
    val tabs: @Composable () -> Unit = {
        tabState.titles.forEachIndexed { index, titleId ->
            ALTab(
                selected = index == tabState.currentIndex,
                onClick = {
                    switchTab(index)
                    // Deselect any selected template when the user switches tabs.
                    // TODO: Maybe remove this if it gets annoying for the user
                    onTemplateClick(-1)
                },
                text = { Text(text = titleId.displayName) }
            )
        }
    }

    Column(modifier) {
        if (shouldUseScrollableTabRow) {
            ALScrollableTabRow(
                selectedTabIndex = tabState.currentIndex,
                modifier = Modifier.fillMaxWidth(),
                tabs = tabs
            )
        } else {
            ALTabRow(
                selectedTabIndex = tabState.currentIndex,
                modifier = Modifier.fillMaxWidth(),
                tabs = tabs
            )
        }

        TemplatesTabContent(
            templates = templates,
            onTemplateClick = onTemplateClick,
            selectedIndex = selectedIndex
        )
    }
}