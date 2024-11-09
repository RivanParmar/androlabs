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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.rivan.androlabs.core.designsystem.component.ALFloatingActionButton
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.ui.LabPreviewParameterProvider
import com.rivan.androlabs.core.ui.ProjectFeedUiState
import com.rivan.androlabs.core.ui.projectFeed

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
    onFloatingActionButtonClick: () -> Unit,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var searchBarIsActive by rememberSaveable { mutableStateOf(false) }

    var showClearRecentSearchDialog by remember { mutableStateOf(false) }
    var recentSearchQueryToBeCleared by remember { mutableStateOf("") }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
    ) {
        if (isSyncing || labFeedUIState is ProjectFeedUiState.Loading) {
            LoadingState()
        } else if (labFeedUIState is ProjectFeedUiState.Success) {
            if (labFeedUIState.feed.isEmpty()) {
                EmptyState(titleRes = R.string.labs_grid_empty)
            } else {
                if (contentType == ContentType.DUAL_PANE) {
                    // TODO: Fix padding
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(300.dp),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = modifier
                            .padding(top = 72.dp, bottom = 16.dp)
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
                        modifier = modifier
                            .padding(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp)
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

        ALFloatingActionButton(
            onClick = onFloatingActionButtonClick,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            elevation = FloatingActionButtonDefaults.elevation(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 32.dp),
        ) {
            Icon(
                imageVector = ALIcons.Add,
                contentDescription = null,
            )
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
                // TODO: Set a proper title
                Text(text = "Delete $recentSearchQueryToBeCleared from recent?")
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
        )
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
@Preview
@Composable
private fun HomeScreenLayoutGridPreview(
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