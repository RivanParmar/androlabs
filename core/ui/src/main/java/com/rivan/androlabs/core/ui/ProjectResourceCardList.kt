package com.rivan.androlabs.core.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import com.rivan.androlabs.core.domain.model.UserProjectListResource

/**
 * Extension function for displaying a [List] of [ProjectResourceCard] backed by a list of
 * [UserProjectListResource]s.
 *
 * [onItemClick] optional parameter for action to be performed when the card is clicked.
 */
fun LazyListScope.projectResourceCardItems(
    items: List<UserProjectListResource>,
    onToggleFavourite: (item: UserProjectListResource) -> Unit,
    onItemClick: ((item: UserProjectListResource) -> Unit)? = null,
    onResourceTypeClick: ((String) -> Unit)? = null,
    itemModifier: Modifier = Modifier
) = items(
    items = items,
    key = { it.id },
    itemContent = {userProjectListResource ->
        ProjectResourceCard(
            userProjectListResource = userProjectListResource,
            isFavourite = userProjectListResource.isFavourite,
            isCompleted = userProjectListResource.isCompleted,
            onToggleFavourite = { onToggleFavourite(userProjectListResource) },
            onClick = {
                when (onItemClick) {
                    // TODO: Not yet implemented
                }
            },
            modifier = itemModifier
        )
    }
)