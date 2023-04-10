package com.rivan.androlabs.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rivan.androlabs.feature.settings.SettingsRoute

const val settingsNavigationRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingScreen() {
    composable(route = settingsNavigationRoute) {
        SettingsRoute()
    }
}