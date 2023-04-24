package com.rivan.androlabs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rivan.androlabs.feature.npw.navigation.newProjectWizardScreen
import com.rivan.androlabs.feature.recent.navigation.recentNavigationRoute
import com.rivan.androlabs.feature.recent.navigation.recentScreen
import com.rivan.androlabs.feature.settings.navigation.createSettingsGraph

/**
 * Top-level navigation graph.
 *
 * The navigation graph defined in this file defines the different top-level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun ALNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = recentNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        recentScreen()
        createSettingsGraph(
            onSettingsCategoryItemClick = { settingsCategoryRoute ->
                navController.navigate("settings_graph/$settingsCategoryRoute/")
            }
        )
        // This is not a top-level destination but will be navigated to from all of the top-level
        // destinations except Settings. Therefore, we avoid nesting it with multiple destinations.
        newProjectWizardScreen(onBackClick = navController::popBackStack)
    }
}