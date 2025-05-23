package com.rivan.androlabs.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuScope
import androidx.compose.material3.Icon
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex

/**
 * Androlabs floating action button with icon content slot. Wraps Material 3
 * [FloatingActionButton].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param elevation The elevation to applied to the button.
 * @param modifier Modifier to be applied to the button.
 * @param containerColor The background color to be used for the button.
 * @param icon The button icon content.
 */
@Composable
fun ALFloatingActionButton(
    onClick: () -> Unit,
    elevation: FloatingActionButtonElevation,
    modifier: Modifier = Modifier,
    containerColor: Color,
    icon: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = elevation,
        modifier = modifier,
        containerColor = containerColor,
        content = icon
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ALFloatingActionButtonMenu(
    expanded: Boolean,
    visible: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable FloatingActionButtonMenuScope.() -> Unit,
) {
    FloatingActionButtonMenu(
        modifier = modifier,
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                modifier = Modifier
                    .semantics {
                    traversalIndex = -1f
                    stateDescription = if (expanded) "Expanded" else "Collapsed"
                    contentDescription = "Toggle menu"
                    }
                    .animateFloatingActionButton(
//                         TODO: Check if FAB is visible
                        visible = visible || expanded,
                        alignment = Alignment.BottomEnd,
                    ),
                checked = expanded,
                onCheckedChange = onCheckedChange,
            ) {
                val imageVector by remember {
                    derivedStateOf {
                        if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                    }
                }
                Icon(
                    painter = rememberVectorPainter(imageVector),
                    contentDescription = null,
                    modifier = Modifier.animateIcon({ checkedProgress }),
                )
            }
        },
        content = content,
    )
}