package com.rivan.androlabs.core.designsystem.component

import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Andro Labs floating action button with icon content slot. Wraps Material 3
 * [FloatingActionButton].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param icon The button icon content.
 */
@Composable
fun ALFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        content = icon
    )
}
