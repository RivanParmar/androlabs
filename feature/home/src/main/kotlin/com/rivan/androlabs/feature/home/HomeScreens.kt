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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.component.ALFloatingActionButton
import com.rivan.androlabs.core.designsystem.component.ALTopAppBarLarge
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.ListType
import com.rivan.androlabs.core.model.data.UserLabs
import com.rivan.androlabs.core.ui.LabFeedUIState
import com.rivan.androlabs.core.ui.labFeed

// TODO: Cleanup the code if possible
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun HomeScreenLabsGrid(
    contentType: ContentType,
    listType: ListType,
    labFeedUIState: LabFeedUIState,
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    lazyListState: LazyListState = rememberLazyListState(),
    isSyncing: Boolean = false,
    onSearch: (String) -> Unit,
    onFloatingActionButtonClick: () -> Unit,
    navigateToDetail: (String, ContentType) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // TODO: Wrap the entire Scaffold with ALBackground after changing to a suitable theme
    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            // TODO: Animate this!
            if ((!active && contentType == ContentType.SINGLE_PANE)
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
                text = text,
                active = active,
                recentSearchQueriesUiState = recentSearchQueriesUiState,
                onSearch = onSearch,
                onTextChange = { text = it },
                onActiveChange = { active = it },
            )

            if (isSyncing || labFeedUIState.loading) {
                LoadingState()
            } else if (labFeedUIState.labs.isEmpty()) {
                EmptyState(titleRes = R.string.labs_grid_empty)
            } else {
                if (listType == ListType.GRID) {
                    LazyVerticalGrid(
                        columns = Adaptive(300.dp),
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
                        labFeed(
                            labFeedUIState = labFeedUIState,
                            contentType = contentType,
                            onClick = { labId, screenContentType ->
                                active = false
                                navigateToDetail(labId, screenContentType)
                            },
                        )
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                        }
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
                            .clip(RoundedCornerShape(20.dp)),
                            // TODO: Change this color as per required
                            //.background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        labFeed(
                            labFeedUIState = labFeedUIState,
                            contentType = contentType,
                            onClick = { labId, screenContentType ->
                                active = false
                                navigateToDetail(labId, screenContentType)
                            },
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenLabsDetail(
    lab: UserLabs?,
    closeDetailScreen: () -> Unit,
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
) {
    val scrollBehavior = TopAppBarDefaults
        .exitUntilCollapsedScrollBehavior(topAppBarState)

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            if (lab != null) {
                ALTopAppBarLarge(
                    title = lab.title,
                    navigationIcon = ALIcons.Back,
                    navigationIconContentDescription = null,
                    actionIcon = Icons.Outlined.MoreVert,
                    actionIconContentDescription = null,
                    onNavigationClick = closeDetailScreen,
                    scrollBehavior = scrollBehavior,
                )
            }
        },
    ) { padding ->

        Column(
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
            if (lab == null) {
                EmptyState(titleRes = R.string.labs_detail_empty)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item { Text(text = "Hello") }
                }
            }
        }
    }
}