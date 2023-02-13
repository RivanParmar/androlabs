package com.rivan.androlabs.navigation

import com.rivan.androlabs.R
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.designsystem.icon.Icon
import com.rivan.androlabs.core.designsystem.icon.Icon.*
import com.rivan.androlabs.feature.recent.R as recentR
import com.rivan.androlabs.feature.settings.R as settingsR

enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int
) {
    RECENT(
        selectedIcon = DrawableResourceIcon(ALIcons.Recent),
        unselectedIcon = DrawableResourceIcon(ALIcons.RecentBorder),
        iconTextId = recentR.string.recent,
        titleTextId = R.string.app_name
    ),
    SETTINGS(
        selectedIcon = DrawableResourceIcon(ALIcons.Settings),
        unselectedIcon = DrawableResourceIcon(ALIcons.SettingsBorder),
        iconTextId = settingsR.string.settings,
        titleTextId = settingsR.string.settings
    )
}