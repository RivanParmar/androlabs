package com.rivan.androlabs.core.designsystem.component

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Andro Labs floating action button with icon content slot. Wraps Material 3
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
