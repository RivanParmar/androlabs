package com.rivan.androlabs.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.window.layout.DisplayFeature
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.feature.settings.SettingsRoute
import com.rivan.androlabs.feature.settings.categories.aboutScreen
import com.rivan.androlabs.feature.settings.categories.generalGraph

private const val settingsGraphRoutePattern = "settings_graph"
const val settingsRoute = "settings_route"

fun NavController.navigateToSettingsGraph(navOptions: NavOptions? = null) {
    this.navigate(settingsRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen(
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    onSettingsCategoryItemClick: (String) -> Unit
) {
    composable(route = settingsRoute) {
        SettingsRoute(contentType, displayFeatures, onItemClick = onSettingsCategoryItemClick)
    }
    navigation(startDestination = "general_graph", route = settingsGraphRoutePattern) {
        generalGraph()
        aboutScreen()
    }
}