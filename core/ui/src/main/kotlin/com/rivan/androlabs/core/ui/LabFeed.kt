package com.rivan.androlabs.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.model.data.UserLabs

// TODO: Rename onProjectResourcesCheckedChanged
/**
 * An extension on [LazyGridScope] defining a feed with project resources.
 * Depending on the [feedState], this might emit no items.
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.projectFeed(
    feedState: ProjectFeedUiState,
    onClick: () -> Unit,
    onProjectResourcesCheckedChanged: (String, Boolean) -> Unit,
) {
    when (feedState) {
        ProjectFeedUiState.Loading -> Unit
        is ProjectFeedUiState.Success -> {
            items(feedState.feed, key = { it.id }) { userLabs ->
                LabCard(
                    userLabs = userLabs,
                    isFavourite = userLabs.isFavourite,
                    isCompleted = userLabs.isCompleted,
                    onToggleFavourite = {
                        onProjectResourcesCheckedChanged(
                            userLabs.id,
                            !userLabs.isFavourite
                        )
                    },
                    onClick = onClick,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
        is ProjectFeedUiState.Error -> Unit
    }
}

/**
 * An extension on [LazyListScope] defining a feed with project resources.
 * Depending on the [feedState], this might emit no items.
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.projectFeed(
    feedState: ProjectFeedUiState,
    onClick: () -> Unit,
) {
    when (feedState) {
        ProjectFeedUiState.Loading -> Unit
        is ProjectFeedUiState.Success -> {
            items(feedState.feed, key = { it.id }) { userLab ->
                LabListItem(
                    userLab = userLab,
                    onClick = onClick,
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
        is ProjectFeedUiState.Error -> Unit
    }
}

// TODO: Rename this to LabFeedUiState (and other related items)
/**
 * A sealed hierarchy describing the state of the feed of project resources.
 */
sealed interface ProjectFeedUiState {
    /**
     * The feed is still loading.
     */
    data object Loading : ProjectFeedUiState

    /**
     * The feed is loaded with the given list of project resources.
     */
    data class Success(
        /**
         * The list of project resources contained in this feed.
         */
        val feed: List<UserLabs>,
    ) : ProjectFeedUiState

    data class Error(
        val message: String?,
    ) : ProjectFeedUiState
}

@Preview
@Composable
private fun ProjectFeedLoadingPreview() {
    AndrolabsTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            projectFeed(
                feedState = ProjectFeedUiState.Loading,
                onClick = {},
            ) { _, _ -> }
        }
    }
}

@Preview
@Preview(device = Devices.TABLET)
@Composable
private fun ProjectFeedContentPreview(
    @PreviewParameter(UserLabPreviewParameterProvider::class)
    userLabs: List<UserLabs>
) {
    AndrolabsTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            projectFeed(
                feedState = ProjectFeedUiState.Success(userLabs),
                onClick = {},
            ) { _, _ -> }
        }
    }
}