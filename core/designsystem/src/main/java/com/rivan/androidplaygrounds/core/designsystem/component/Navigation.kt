package com.rivan.androidplaygrounds.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * Android Playgrounds navigation rail with header and content slots. Wraps Material 3
 * [NavigationRail].
 *
 * @param modifier Modifier to be applied to the navigation rail.
 * @param header Optional header that may hold a floating action button or a logo.
 * @param content Destinations inside navigation rail. This should contain multiple
 * [NavigationRailItem]s.
 */
@Composable
fun APNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = APNavigationDefaults.navigationContentColor(),
        header = header,
        content = content
    )
}

/**
 * Android Playgrounds navigation rail item with icon and label content slots. Wraps Material 3
 * [NavigationRailItem].
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
fun APNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (enabled) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
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