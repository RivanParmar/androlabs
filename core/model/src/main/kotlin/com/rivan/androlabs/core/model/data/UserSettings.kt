package com.rivan.androlabs.core.model.data

/**
 * Class summarizing user settings.
 */
data class UserSettings(
    val themeType: ThemeType,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,

    val savePath: String,
)
