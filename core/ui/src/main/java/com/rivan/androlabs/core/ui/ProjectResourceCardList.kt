package com.rivan.androlabs.core.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.rivan.androlabs.core.domain.model.UserProjectResource
import com.rivan.androlabs.core.model.data.ProjectResource

/**
 * Extension function for displaying a [List] of [ProjectResourceCard] backed by a list of
 * [UserProjectResource]s.
 *
 * [onItemClick] optional parameter for action to be performed when the card is clicked.
 */
fun LazyListScope.projectResourceCardItems(
    items: List<UserProjectResource>,
    onToggleFavourite: (item: UserProjectResource) -> Unit,
    onItemClick: ((item: UserProjectResource) -> Unit)? = null,
    onResourceTypeClick: ((String) -> Unit)? = null,
    itemModifier: Modifier = Modifier
) = items(
    items = items,
    key = { it.id },
    itemContent = {userProjectResource ->
        ProjectResourceCard(
            userProjectResource = userProjectResource,
            isFavourite = userProjectResource.isFavourite,
            isCompleted = userProjectResource.isCompleted,
            onToggleFavourite = { onToggleFavourite(userProjectResource) },
            onClick = {
                when (onItemClick) {
                    // TODO: Not yet implemented
                }
            },
            onResourceTypeClick = onResourceTypeClick,
            modifier = itemModifier
        )
    }
)