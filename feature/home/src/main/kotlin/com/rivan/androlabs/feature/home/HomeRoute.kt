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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.ListType
import com.rivan.androlabs.core.ui.ProjectFeedUiState

@Composable
internal fun HomeRoute(
    contentType: ContentType,
    listType: ListType,
    onAccountButtonClick: () -> Unit,
    onFABClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val labFeedUIState by viewModel.projectFeedUiState.collectAsStateWithLifecycle()
    val recentSearchQueriesUiState by viewModel.recentSearchQueriesUiState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    HomeScreen(
        contentType = contentType,
        listType = listType,
        labFeedUIState = labFeedUIState,
        isSyncing = isSyncing,
        recentSearchQueriesUiState = recentSearchQueriesUiState,
        onAccountButtonClick = onAccountButtonClick,
        onSearch = viewModel::onSearchTriggered,
        onRecentSearchDelete = viewModel::clearRecentSearch,
        onClearRecentSearches = viewModel::clearRecentSearches,
        onFABClick = onFABClick,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    contentType: ContentType,
    listType: ListType,
    labFeedUIState: ProjectFeedUiState,
    recentSearchQueriesUiState: RecentSearchQueriesUiState,
    isSyncing: Boolean,
    onAccountButtonClick: () -> Unit,
    onSearch: (String) -> Unit,
    onRecentSearchDelete: (String) -> Unit,
    onClearRecentSearches: () -> Unit,
    onFABClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeScreenLabsGrid(
        contentType = contentType,
        listType = listType,
        labFeedUIState = labFeedUIState,
        recentSearchQueriesUiState = recentSearchQueriesUiState,
        modifier = modifier,
        isSyncing = isSyncing,
        onAccountButtonClick = onAccountButtonClick,
        onSearch = onSearch,
        onRecentSearchDelete = onRecentSearchDelete,
        onClearRecentSearches = onClearRecentSearches,
        onFloatingActionButtonClick = onFABClick,
    )
}