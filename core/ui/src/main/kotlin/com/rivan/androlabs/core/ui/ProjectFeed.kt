package com.rivan.androlabs.core.ui

import androidx.compose.foundation.lazy.grid.*
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
    onProjectResourcesCheckedChanged: (String, Boolean) -> Unit
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
                    onClick = { /*TODO*/ }
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
                feedState = ProjectFeedUiState.Loading
            ) { _, _ -> }
        }
    }
}

@Preview
@Preview(device = Devices.TABLET)
@Composable
private fun ProjectFeedContentPreview(
    @PreviewParameter(UserProjectResourcePreviewParameterProvider::class)
    UserProjectResource: List<UserProjectResource>
) {
    AndroLabsTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            projectFeed(
                feedState = ProjectFeedUiState.Success(UserProjectResource)
            ) { _, _ -> }
        }
    }
}