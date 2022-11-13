package com.rivan.androidplaygrounds.navigation

import com.rivan.androidplaygrounds.R
import com.rivan.androidplaygrounds.core.designsystem.icon.APIcons
import com.rivan.androidplaygrounds.core.designsystem.icon.Icon
import com.rivan.androidplaygrounds.core.designsystem.icon.Icon.DrawableResourceIcon
import com.rivan.androidplaygrounds.feature.recent.R as recentR
import com.rivan.androidplaygrounds.feature.settings.R as settingsR

enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int
) {
    RECENT(
        selectedIcon = DrawableResourceIcon(APIcons.Recent),
        unselectedIcon = DrawableResourceIcon(APIcons.RecentBorder),
        iconTextId = recentR.string.recent,
        titleTextId = R.string.app_name
    ),
    SETTINGS(
        selectedIcon = DrawableResourceIcon(APIcons.Settings),
        unselectedIcon = DrawableResourceIcon(APIcons.SettingsBorder),
        iconTextId = settingsR.string.settings,
        titleTextId = settingsR.string.settings
    )
}