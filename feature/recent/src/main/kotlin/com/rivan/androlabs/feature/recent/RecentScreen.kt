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

package com.rivan.androlabs.feature.recent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.domain.model.UserLabs
import com.rivan.androlabs.core.ui.DevicePreviews
import com.rivan.androlabs.core.ui.ProjectFeedUiState
import com.rivan.androlabs.core.ui.ProjectFeedUiState.Loading
import com.rivan.androlabs.core.ui.ProjectFeedUiState.Success
import com.rivan.androlabs.core.ui.UserLabPreviewParameterProvider
import com.rivan.androlabs.core.ui.projectFeed

@Composable
internal fun RecentRoute(
    modifier: Modifier = Modifier,
    viewModel: RecentViewModel = hiltViewModel()
) {
    val feedState by viewModel.feedState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    RecentScreen(
        isSyncing = isSyncing,
        feedState = feedState,
        modifier = modifier,
        onProjectResourcesCheckedChanged = viewModel::updateProjectResourceFavourited
    )
}

@Composable
internal fun RecentScreen(
    isSyncing: Boolean,
    feedState: ProjectFeedUiState,
    modifier: Modifier = Modifier,
    onProjectResourcesCheckedChanged: (String, Boolean) -> Unit
) {
    val isFeedLoading = feedState is Loading

    if (isSyncing || isFeedLoading) {
        LoadingState(modifier)
    }

    if (feedState is Success) {
        if (feedState.feed.isNotEmpty()) {
            RecentGrid(feedState, modifier, onProjectResourcesCheckedChanged)
        } else {
            EmptyState(modifier)
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .testTag("recent:loading")
        )
    }
}

@Composable
private fun RecentGrid(
    feedState: ProjectFeedUiState,
    modifier: Modifier = Modifier,
    onProjectResourcesCheckedChanged: (String, Boolean) -> Unit
) {
    val scrollableState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = Adaptive(300.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = scrollableState,
        modifier = modifier
            .fillMaxSize()
            .testTag("recent:feed"),
    ) {
        projectFeed(
            feedState = feedState,
            onProjectResourcesCheckedChanged = onProjectResourcesCheckedChanged
        )
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("recent:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: Maybe add gradient tint to the icon
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_empty_recent),
            contentDescription = null
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No recent projects",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Recently opened projects will be shown here",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@DevicePreviews
@Composable
fun RecentScreenPopulatedFeed(
    @PreviewParameter(UserLabPreviewParameterProvider::class)
    userLabs: List<UserLabs>
) {
    BoxWithConstraints {
        AndrolabsTheme {
            RecentScreen(
                isSyncing = false,
                feedState = ProjectFeedUiState.Success(
                    feed = userLabs
                )
            ) { _, _ -> }
        }
    }
}

@DevicePreviews
@Composable
fun RecentScreenLoading() {
    BoxWithConstraints {
        AndrolabsTheme {
            RecentScreen(
                isSyncing = false,
                feedState = Loading
            ) { _, _ -> }
        }
    }
}

@DevicePreviews
@Composable
fun RecentScreenPopulatedAndLoading(
    @PreviewParameter(UserLabPreviewParameterProvider::class)
    userLabs: List<UserLabs>
) {
    BoxWithConstraints {
        AndrolabsTheme {
            RecentScreen(
                isSyncing = true,
                feedState = ProjectFeedUiState.Success(
                    feed = userLabs
                )
            ) { _, _ -> }
        }
    }
}