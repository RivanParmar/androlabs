package com.rivan.androlabs.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rivan.androlabs.feature.settings.SettingsRoute

private const val settingsGraphRoutePattern = "settings_graph"
const val settingsRoute = "settings_route"

fun NavController.navigateToSettingsGraph(navOptions: NavOptions? = null) {
    this.navigate(settingsGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.settingsGraph(
    onRootSettingsOptionClick: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = settingsGraphRoutePattern,
        startDestination = settingsRoute
    ) {
        composable(route = settingsRoute) {
            SettingsRoute(onRootSettingsOptionClick)
        }
        nestedGraphs()
    }
}