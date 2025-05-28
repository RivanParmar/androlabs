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

package com.rivan.androlabs.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.rivan.androlabs.core.designsystem.component.ALFloatingActionButtonMenu
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.ui.LabPreviewParameterProvider
import com.rivan.androlabs.core.ui.ProjectFeedUiState
import com.rivan.androlabs.core.ui.projectFeed
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenLayout(
    contentType: ContentType,
    labFeedUIState: ProjectFeedUiState,
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    modifier: Modifier = Modifier,
    isSyncing: Boolean = false,
    onAccountButtonClick: () -> Unit,
    onSearch: (String) -> Unit,
    onRecentSearchDelete: (String) -> Unit,
    onClearRecentSearches: () -> Unit,
    onLabItemClick: () -> Unit,
    onFloatingActionButtonClick: (String) -> Unit,
) {
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()

    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    var showClearRecentSearchDialog by remember { mutableStateOf(false) }
    var recentSearchQueryToBeCleared by remember { mutableStateOf("") }

    val listState = rememberLazyListState()
    val fabVisible by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeScreenSearchBar(
                scrollBehavior = scrollBehavior,
                contentType = contentType,
                recentSearchQueriesUiState = recentSearchQueriesUiState,
                onSearch = {
                    if (it.isNotEmpty() && it.isNotBlank()) {
                        onSearch(it)
                        scope.launch { searchBarState.animateToCollapsed() }
                    }
                },
                onLeadingIconClick = {
                    // TODO: Why is this not working?
                    scope.launch { searchBarState.animateToCollapsed() }
                },
                onTrailingIconClick = onAccountButtonClick,
                onSearchItemClick = {
                    textFieldState.setTextAndPlaceCursorAtEnd(it)
                    scope.launch { searchBarState.animateToCollapsed() }
                },
                onRecentSearchDelete = {
                    showClearRecentSearchDialog = true
                    recentSearchQueryToBeCleared = it
                },
                // TODO: Maybe we should show confirmation dialog before clearing searches
                onClearRecentSearches = onClearRecentSearches,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                visible = fabVisible,
                onFABMenuItemClick = onFloatingActionButtonClick,
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        contentWindowInsets = WindowInsets(0.dp),
    ) { padding ->
        if (isSyncing || labFeedUIState is ProjectFeedUiState.Loading) {
            LoadingState()
        } else if (labFeedUIState is ProjectFeedUiState.Success) {
            if (labFeedUIState.feed.isEmpty()) {
                EmptyState(titleRes = R.string.labs_grid_empty)
            } else {
                if (contentType == ContentType.DUAL_PANE) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(300.dp),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = modifier
                            .padding(padding)
                            .consumeWindowInsets(padding)
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
                            .semantics { traversalIndex = 1f },
                    ) {
                        projectFeed(
                            feedState = labFeedUIState,
                            onClick = onLabItemClick,
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState,
                        modifier = modifier
                            .padding(padding)
                            .consumeWindowInsets(padding)
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .semantics { traversalIndex = 1f },
                    ) {
                        projectFeed(
                            feedState = labFeedUIState,
                            onClick = onLabItemClick,
                        )
                    }
                }
            }
        } else {
            // TODO: Use error state here
        }
    }

    if (showClearRecentSearchDialog) {
        AlertDialog(
            onDismissRequest = { showClearRecentSearchDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRecentSearchDelete(recentSearchQueryToBeCleared)
                        showClearRecentSearchDialog = false
                    },
                ) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearRecentSearchDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(
                    text = stringResource(
                        R.string.delete_search_item_confirmation,
                        recentSearchQueryToBeCleared,
                    ),
                )
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FloatingActionButton(
    visible: Boolean,
    modifier: Modifier = Modifier,
    onFABMenuItemClick: (String) -> Unit,
) {
    val items =
        listOf(
            Icons.Filled.ImportExport to "VCS",
            Icons.Filled.FolderOpen to "Open",
            Icons.Filled.Add to "New",
        )

    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

    ALFloatingActionButtonMenu(
        expanded = fabMenuExpanded,
        visible = visible,
        onCheckedChange = { fabMenuExpanded = !fabMenuExpanded },
        modifier = modifier,
    ) {
        items.forEachIndexed { i, item ->
            FloatingActionButtonMenuItem(
                modifier = Modifier
                    .semantics {
                        isTraversalGroup = true

                        if (i == items.size - 1) {
                            customActions =
                                listOf(
                                    CustomAccessibilityAction(
                                        label = "Close menu",
                                        action = {
                                            fabMenuExpanded = false
                                            true
                                        }
                                    )
                                )
                        }
                    },
                onClick = {
                    fabMenuExpanded = false
                    onFABMenuItemClick(item.second)
                },
                icon = { Icon(item.first, contentDescription = null) },
                text = { Text(text = item.second) },
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenLayoutLoadingPreview() {
    AndrolabsTheme {
        HomeScreenLayout(
            contentType = ContentType.SINGLE_PANE,
            labFeedUIState = ProjectFeedUiState.Loading,
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Success(),
            onAccountButtonClick = {},
            onSearch = {},
            onRecentSearchDelete = {},
            onClearRecentSearches = {},
            onLabItemClick = {},
            onFloatingActionButtonClick = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenLayoutPreview(
    @PreviewParameter(LabPreviewParameterProvider::class)
    labs: List<Lab>,
) {
    AndrolabsTheme {
        HomeScreenLayout(
            contentType = ContentType.SINGLE_PANE,
            labFeedUIState = ProjectFeedUiState.Success(labs),
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Success(),
            onAccountButtonClick = {},
            onSearch = {},
            onRecentSearchDelete = {},
            onClearRecentSearches = {},
            onLabItemClick = {},
            onFloatingActionButtonClick = {},
        )
    }
}

// TODO: Use large screen preview here
@Preview(device = "id:pixel_tablet")
@Composable
private fun HomeScreenLayoutGridPreview(
    @PreviewParameter(LabPreviewParameterProvider::class)
    labs: List<Lab>,
) {
    AndrolabsTheme {
        HomeScreenLayout(
            contentType = ContentType.DUAL_PANE,
            labFeedUIState = ProjectFeedUiState.Success(labs),
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Success(),
            onAccountButtonClick = {},
            onSearch = {},
            onRecentSearchDelete = {},
            onClearRecentSearches = {},
            onLabItemClick = {},
            onFloatingActionButtonClick = {},
        )
    }
}