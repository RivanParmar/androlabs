package com.rivan.androlabs.core.model.data

/**
 * Class summarizing user data.
 */
data class UserData(
    val savedProjectResources: Set<String>,
    val completedProjectResources: Set<String>,
    val themeType: ThemeType,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean
)
