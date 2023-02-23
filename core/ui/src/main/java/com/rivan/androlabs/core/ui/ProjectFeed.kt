package com.rivan.androlabs.core.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndroLabsTheme
import com.rivan.androlabs.core.domain.model.UserProjectResource

/**
 * An extension on [LazyGridScope] defining a feed with project resources.
 * Depending on the [feedState], this might emit no items.
 */
fun LazyGridScope.projectFeed(
    feedState: ProjectFeedUiState,
    onProjectResourcesCheckedChanged: (String, Boolean) -> Unit,
    onResourceTypeClick: ((String) -> Unit)?
) {
    when (feedState) {
        ProjectFeedUiState.Loading -> Unit
        is ProjectFeedUiState.Success -> {
            items(feedState.feed, key = { it.id }) { userProjectResource ->
                ProjectResourceCard(
                    userProjectResource = userProjectResource,
                    isFavourite = userProjectResource.isFavourite,
                    isCompleted = userProjectResource.isCompleted,
                    onToggleFavourite = {
                        onProjectResourcesCheckedChanged(
                            userProjectResource.id,
                            !userProjectResource.isFavourite
                        )
                    },
                    onClick = { /*TODO*/ },
                    onResourceTypeClick = onResourceTypeClick
                )
            }
        }
    }
}

/**
 * A sealed hierarchy describing the state of the feed of project resources.
 */
sealed interface ProjectFeedUiState {
    /**
     * The feed is still loading.
     */
    object Loading : ProjectFeedUiState

    /**
     * The feed is loaded with the given list of project resources.
     */
    data class Success(
        /**
         * The list of project resources contained in this feed.
         */
        val feed: List<UserProjectResource>
    ) : ProjectFeedUiState
}

@Preview
@Composable
private fun ProjectFeedLoadingPreview() {
    AndroLabsTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            projectFeed(
                feedState = ProjectFeedUiState.Loading,
                onProjectResourcesCheckedChanged = { _, _ -> },
                onResourceTypeClick = {}
            )
        }
    }
}

@Preview
@Preview(device = Devices.TABLET)
@Composable
private fun ProjectFeedContentPreview(
    @PreviewParameter(UserProjectResourcePreviewParameterProvider::class)
    userProjectResource: List<UserProjectResource>
) {
    AndroLabsTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            projectFeed(
                feedState = ProjectFeedUiState.Success(userProjectResource),
                onProjectResourcesCheckedChanged = { _, _ -> },
                onResourceTypeClick = {}
            )
        }
    }
}