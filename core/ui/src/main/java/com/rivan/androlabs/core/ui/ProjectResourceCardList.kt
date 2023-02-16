package com.rivan.androlabs.core.ui

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.rivan.androlabs.core.model.data.ProjectResource

/**
 * Extension function for displaying a [List] of [ProjectResourceCard] backed by a list of
 * [ProjectResource]s.
 *
 * [onItemClick] optional parameter for action to be performed when the card is clicked.
 */
fun LazyListScope.projectResourceCardItems(
    items: List<ProjectResource>,
    onItemClick: ((item: ProjectResource) -> Unit)? = null,
    itemModifier: Modifier = Modifier
) = items(
    items = items,
    key = { it.id },
    itemContent = {projectResource ->
        val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
        val context = LocalContext.current

        ProjectResourceCard(
            projectResource = projectResource,
            onClick = {
                when (onItemClick) {
                    // TODO: Not yet implemented
                }
            },
            modifier = itemModifier
        )
    }
)