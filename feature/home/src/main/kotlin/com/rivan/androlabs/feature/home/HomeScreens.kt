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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.rivan.androlabs.core.designsystem.component.ALFloatingActionButton
import com.rivan.androlabs.core.designsystem.component.ALScaffold
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.ListType
import com.rivan.androlabs.core.ui.ProjectFeedUiState
import com.rivan.androlabs.core.ui.projectFeed

// TODO: Cleanup the code if possible
@Composable
internal fun HomeScreenLabsGrid(
    contentType: ContentType,
    listType: ListType,
    labFeedUIState: ProjectFeedUiState,
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    lazyListState: LazyListState = rememberLazyListState(),
    isSyncing: Boolean = false,
    onAccountButtonClick: () -> Unit,
    onSearch: (String) -> Unit,
    onRecentSearchDelete: (String) -> Unit,
    onClearRecentSearches: () -> Unit,
    onFloatingActionButtonClick: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchBarIsActive by remember { mutableStateOf(false) }

    var showClearRecentSearchDialog by remember { mutableStateOf(false) }
    var recentSearchQueryToBeCleared by remember { mutableStateOf("") }

    // TODO: Wrap the entire Scaffold with ALBackground after changing to a suitable theme
    ALScaffold(
        floatingActionButton = {
            // Hide the FAB in case the search bar is active while the screen size is small
            // TODO: Animate this!
            if ((!searchBarIsActive && contentType == ContentType.SINGLE_PANE)
                || (contentType == ContentType.DUAL_PANE)) {
                ALFloatingActionButton(
                    onClick = onFloatingActionButtonClick,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    elevation = FloatingActionButtonDefaults.elevation(),
                ) {
                    Icon(
                        imageVector = ALIcons.Add,
                        contentDescription = null,
                    )
                }
            }
        },
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            // TODO: For now the search bar is static at the top but we need to hide/show it
            //  based on scrolling of content similar to the FAB.
            HomeScreenSearchBar(
                contentType = contentType,
                text = searchQuery,
                active = searchBarIsActive,
                recentSearchQueriesUiState = recentSearchQueriesUiState,
                onAccountButtonClick = onAccountButtonClick,
                onSearch = onSearch,
                onTextChange = { searchQuery = it },
                onActiveChange = { searchBarIsActive = it },
                onRecentSearchDelete = {
                    showClearRecentSearchDialog = true
                    recentSearchQueryToBeCleared = it
                },
                onClearRecentSearches = onClearRecentSearches,
            )

            if (isSyncing || labFeedUIState is ProjectFeedUiState.Loading) {
                LoadingState()
            } else if (labFeedUIState is ProjectFeedUiState.Error) {
                // TODO: Use proper error state here
                EmptyState(titleRes = R.string.labs_grid_empty)
            } else {
                if (listType == ListType.GRID) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(300.dp),
                        contentPadding = PaddingValues(
                            start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp
                        ),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        state = lazyGridState,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(
                                top = if (contentType == ContentType.SINGLE_PANE) {
                                    96.dp
                                } else {
                                    // TODO: Tweak this padding as per required
                                    72.dp
                                }
                            ),
                    ) {
                        projectFeed(
                            feedState = labFeedUIState,
                            onProjectResourcesCheckedChanged = { _, _ ->

                            }
                        )
                    }
                } else if (listType == ListType.COLUMN) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = lazyListState,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                top = if (contentType == ContentType.SINGLE_PANE) {
                                    104.dp
                                } else {
                                    // TODO: Tweak this padding as per required
                                    72.dp
                                },
                                end = 16.dp,
                                // TODO: Add bottom padding as per required
                            )
                            .clip(RoundedCornerShape(20.dp))
                            // TODO: Change this color as per required
                            .background(MaterialTheme.colorScheme.primaryContainer),
                    ) {
                        /*labFeed(
                            labFeedUIState = labFeedUIState,
                            contentType = contentType,
                            onClick = { labId, screenContentType ->
                                searchBarIsActive = false
                                navigateToDetail(labId, screenContentType)
                            },
                        )*/
                    }
                }
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
                        }
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
                    // TODO: Set a proper title
                    Text(text = "Delete $recentSearchQueryToBeCleared from recents?")
                },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
            )
        }
    }
}