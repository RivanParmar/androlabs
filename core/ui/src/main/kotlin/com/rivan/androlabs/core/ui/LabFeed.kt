package com.rivan.androlabs.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.UserLabs

/**
 * An extension on [LazyGridScope] defining a feed with project resources.
 * Depending on the [feedState], this might emit no items.
 */
@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.projectFeed(
    feedState: ProjectFeedUiState,
    onProjectResourcesCheckedChanged: (String, Boolean) -> Unit
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier.animateItemPlacement()
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
        val feed: List<UserLabs>
    ) : ProjectFeedUiState
}

@Preview
@Composable
private fun ProjectFeedLoadingPreview() {
    AndrolabsTheme {
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
    @PreviewParameter(UserLabPreviewParameterProvider::class)
    UserLabs: List<UserLabs>
) {
    AndrolabsTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            projectFeed(
                feedState = ProjectFeedUiState.Success(UserLabs)
            ) { _, _ -> }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.labFeed(
    labFeedUIState: LabFeedUIState,
    contentType: ContentType,
    onClick: (String, ContentType) -> Unit
) {
    if (!labFeedUIState.loading) {
        items(items = labFeedUIState.labs, key = { it.id }) { userLabResource ->
            LabCard(
                userLabs = userLabResource,
                isFavourite = userLabResource.isFavourite,
                isCompleted = userLabResource.isCompleted,
                onToggleFavourite = { /*TODO*/ },
                onClick = { onClick(userLabResource.id, contentType) },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.labFeed(
    labFeedUIState: LabFeedUIState,
    contentType: ContentType,
    onClick: (String, ContentType) -> Unit
) {
    if (!labFeedUIState.loading) {
        itemsIndexed(items = labFeedUIState.labs) { index, userLabResource ->
            LabListItem(
                userLabs = userLabResource,
                onClick = { onClick(userLabResource.id, contentType) },
                modifier = Modifier.animateItemPlacement()
            )

            if (index < labFeedUIState.labs.lastIndex) {
                // Divider(modifier = Modifier.padding(horizontal = 20.dp)
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

data class LabFeedUIState(
    val labs: List<UserLabs> = emptyList(),
    val selectedLab: UserLabs? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)