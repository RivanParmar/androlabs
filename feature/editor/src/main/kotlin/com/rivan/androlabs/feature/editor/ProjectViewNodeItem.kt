package com.rivan.androlabs.feature.editor

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.rivan.androlabs.core.data.repository.ProjectViewNode

@Composable
fun ProjectViewNodeItem(
    projectViewNode: ProjectViewNode,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(40.dp)
            .padding(start = projectViewNode.depth * 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (projectViewNode.isDirectory) {
            val rotation: Float by animateFloatAsState(
                targetValue = if (projectViewNode.isExpanded) 90f else 0f,
                label = "Chevron Rotation"
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                modifier = Modifier
                    .graphicsLayer {
                        this.rotationZ = rotation
                    },
                contentDescription = null,
            )
        }
        Spacer(
            modifier = Modifier.width(if (projectViewNode.isDirectory) 8.dp else 32.dp)
        )
        Icon(
            imageVector = Icons.Outlined.Folder,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(projectViewNode.name)
    }
}