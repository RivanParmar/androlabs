package com.rivan.androlabs.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.rivan.androlabs.feature.settings.Routes
import com.rivan.androlabs.feature.settings.SettingsRoute
import com.rivan.androlabs.feature.settings.aboutGraph
import com.rivan.androlabs.feature.settings.generalGraph

private const val settingsGraphRoutePattern = "settings_graph"
const val settingsRoute = "settings_route"

fun NavController.navigateToSettingsGraph(navOptions: NavOptions? = null) {
    this.navigate(settingsGraphRoutePattern, navOptions)
}

fun NavGraphBuilder.createSettingsGraph(
    onSettingsCategoryItemClick: (String) -> Unit
) {
    settingsGraph(
        route = settingsGraphRoutePattern,
        startDestination = settingsRoute,
        root = { SettingsRoute(onSettingsCategoryItemClick) }
    ) { subRoute ->
        generalGraph(route = subRoute(Routes.GENERAL_ROUTE))
        aboutGraph(route = subRoute(Routes.ABOUT_ROUTE))
    }
}