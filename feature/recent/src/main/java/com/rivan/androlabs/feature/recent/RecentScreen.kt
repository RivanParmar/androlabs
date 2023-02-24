package com.rivan.androlabs.feature.recent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndroLabsTheme
import com.rivan.androlabs.core.domain.model.UserProjectResource
import com.rivan.androlabs.core.ui.DevicePreviews
import com.rivan.androlabs.core.ui.ProjectFeedUiState
import com.rivan.androlabs.core.ui.UserProjectResourcePreviewParameterProvider
import com.rivan.androlabs.core.ui.projectFeed

@Composable
internal fun RecentRoute(
    modifier: Modifier = Modifier,
    onResourceTypeClick: ((String) -> Unit)? = null,
    viewModel: RecentViewModel = RecentViewModel()
) {
    // This is just a placeholder for now
    // TODO: Add [RecentScreen] here once [RecentViewModel] is ready
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "TODO: RecentScreen not yet implemented!")
    }
}

@Composable
internal fun RecentScreen(
    isSyncing: Boolean,
    feedState: ProjectFeedUiState,
    modifier: Modifier = Modifier,
    onResourceTypeClick: ((String) -> Unit)? = null,
    onProjectResourcesCheckedChanged: (String, Boolean) -> Unit
) {
    //val isFeedLoading = feedState is ProjectFeedUiState.Loading

    val state = rememberLazyGridState()
    
    LazyVerticalGrid(
        columns = Adaptive(300.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier.fillMaxSize(),
        state = state
    ) {
        projectFeed(
            feedState = feedState,
            onProjectResourcesCheckedChanged = onProjectResourcesCheckedChanged,
            onResourceTypeClick = onResourceTypeClick
        )

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
    }
}

@DevicePreviews
@Composable
fun RecentScreenPopulatedFeed(
    @PreviewParameter(UserProjectResourcePreviewParameterProvider::class)
    userProjectResource: List<UserProjectResource>
) {
    BoxWithConstraints {
        AndroLabsTheme {
            RecentScreen(
                isSyncing = false,
                feedState = ProjectFeedUiState.Success(
                    feed = userProjectResource
                ),
                onProjectResourcesCheckedChanged = { _, _ -> }
            )
        }
    }
}