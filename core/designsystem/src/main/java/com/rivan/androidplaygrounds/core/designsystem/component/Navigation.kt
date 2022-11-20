package com.rivan.androidplaygrounds.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Android Playgrounds navigation bar with content slot. Wraps Material 3 [NavigationBar].
 *
 * @param modifier Modifier to be applied to the navigation bar.
 * @param content Destinations inside the navigation bar. This should contain multiple
 * [NavigationBarItem]s.
 */
@Composable
fun APNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        contentColor = APNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

/**
 * Android Playgrounds navigation bar item with icon and label content slots. Wraps Material 3
 * [NavigationBarItem].
 *
 * @param selected Whether this item is selected.
 * @param onClick The callback to be invoked when this item is selected.
 * @param icon The icon for the item.
 * @param modifier Modifier to be applied to this item.
 * @param selectedIcon The icon for the item when selected.
 * @param enabled Controls the enabled state of this item. When 'false', this item will not be
 * clickable and will appear disabled to accessibility services.
 * @param label The text label for the item.
 * @param alwaysShowLabel Whether to always show the label for this item. If 'false', the label will
 * only be shown when this item is selected.
 */
@Composable
fun RowScope.APNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = APNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = APNavigationDefaults.navigationContentColor(),
            selectedTextColor = APNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = APNavigationDefaults.navigationContentColor(),
            indicatorColor = APNavigationDefaults.navigationIndicatorColor()
        )
    )
}

/**
 * Android Playgrounds navigation default values.
 */
object APNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant
    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer
    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}