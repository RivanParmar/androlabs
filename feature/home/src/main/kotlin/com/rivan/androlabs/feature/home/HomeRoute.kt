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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.ListType
import com.rivan.androlabs.core.ui.LabFeedUIState

@Composable
internal fun HomeRoute(
    contentType: ContentType,
    listType: ListType,
    displayFeatures: List<DisplayFeature>,
    onFABClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val labFeedUIState by viewModel.labFeedUiState.collectAsStateWithLifecycle()
    val recentSearchQueriesUiState by viewModel.recentSearchQueriesUiState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    HomeScreen(
        contentType = contentType,
        listType = listType,
        closeDetailScreen = viewModel::closeDetailScreen,
        displayFeatures = displayFeatures,
        labFeedUIState = labFeedUIState,
        isSyncing = isSyncing,
        recentSearchQueriesUiState = recentSearchQueriesUiState,
        onSearch = viewModel::onSearchTriggered,
        navigateToDetail = viewModel::setSelectedLab,
        onFABClick = onFABClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    contentType: ContentType,
    listType: ListType,
    closeDetailScreen: () -> Unit,
    displayFeatures: List<DisplayFeature>,
    labFeedUIState: LabFeedUIState,
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    isSyncing: Boolean,
    onSearch: (String) -> Unit,
    onFABClick: () -> Unit,
    navigateToDetail: (String, ContentType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyGridState: LazyGridState = rememberLazyGridState()
    val lazyListState: LazyListState = rememberLazyListState()
    val labDetailTopAppBarStates =
        labFeedUIState.labs.ifEmpty {
            emptyList()
        }.associate { lab ->
            key(lab.id) {
                lab.id to rememberTopAppBarState()
            }
        }

    if (contentType == ContentType.DUAL_PANE) {
        TwoPane(
            first = {
                HomeScreenLabsGrid(
                    contentType = contentType,
                    listType = listType,
                    labFeedUIState = labFeedUIState,
                    recentSearchQueriesUiState = recentSearchQueriesUiState,
                    modifier = modifier,
                    lazyGridState = lazyGridState,
                    lazyListState = lazyListState,
                    isSyncing = isSyncing,
                    onSearch = onSearch,
                    onFloatingActionButtonClick = onFABClick,
                    navigateToDetail = navigateToDetail,
                )
            },
            second = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                ) {
                    HomeScreenLabsDetail(
                        lab = labFeedUIState.selectedLab,
                        closeDetailScreen = closeDetailScreen,
                        modifier = modifier.clip(RoundedCornerShape(20.dp)),
                        topAppBarState = labDetailTopAppBarStates.getValue(
                            labFeedUIState.selectedLab!!.id
                        ),
                    )
                }
            },
            // TODO: Maybe reduce the gap width and set split fraction to .5f
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = displayFeatures,
        )
    } else {
        HomeScreenSinglePaneContent(
            contentType = contentType,
            listType = listType,
            labFeedUIState = labFeedUIState,
            recentSearchQueriesUiState = recentSearchQueriesUiState,
            isSyncing = isSyncing,
            onSearch = onSearch,
            closeDetailScreen = closeDetailScreen,
            navigateToDetail = navigateToDetail,
            onFABClick = onFABClick,
            modifier = modifier,
            lazyGridState = lazyGridState,
            lazyListState = lazyListState,
            topAppBarState = if (labFeedUIState.selectedLab != null) {
                labDetailTopAppBarStates.getValue(labFeedUIState.selectedLab!!.id)
            } else {
                rememberTopAppBarState()
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenSinglePaneContent(
    contentType: ContentType,
    listType: ListType,
    labFeedUIState: LabFeedUIState,
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    isSyncing: Boolean,
    onSearch: (String) -> Unit,
    onFABClick: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (String, ContentType) -> Unit,
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState,
    lazyListState: LazyListState,
    topAppBarState: TopAppBarState,
) {
    if (labFeedUIState.selectedLab != null && labFeedUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        HomeScreenLabsDetail(
            lab = labFeedUIState.selectedLab,
            closeDetailScreen = closeDetailScreen,
            modifier = modifier,
            topAppBarState = topAppBarState,
        )
    } else {
        HomeScreenLabsGrid(
            contentType = contentType,
            listType = listType,
            labFeedUIState = labFeedUIState,
            recentSearchQueriesUiState = recentSearchQueriesUiState,
            modifier = modifier,
            lazyGridState = lazyGridState,
            lazyListState = lazyListState,
            isSyncing = isSyncing,
            onSearch = onSearch,
            onFloatingActionButtonClick = onFABClick,
            navigateToDetail = navigateToDetail,
        )
    }
}