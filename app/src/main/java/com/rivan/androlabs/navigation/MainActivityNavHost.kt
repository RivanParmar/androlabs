package com.rivan.androlabs.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rivan.androlabs.feature.home.navigation.homeNavigationRoute
import com.rivan.androlabs.feature.home.navigation.homeScreen
import com.rivan.androlabs.feature.settings.navigation.settingsScreen
import com.rivan.androlabs.ui.AndrolabsAppState

/**
 * Top-level navigation graph.
 *
 * The navigation graph defined in this file defines the different top-level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun MainActivityNavHost(
    appState: AndrolabsAppState,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
    askToSelectSavePath: Boolean = false,
    updateSavePath: (String) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) {

    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            contentType = appState.contentType,
            listType = appState.listType,
            displayFeatures = appState.displayFeatures,
            onFABClick = {
                // Ask the user to select a common path for saving the labs
                if (askToSelectSavePath) {
                    launcher.launch(null)
                } else {
                    TODO()
                }
            },
        )

        settingsScreen(appState.contentType, appState.displayFeatures) { settingsCategoryRoute ->
            navController.navigate(settingsCategoryRoute)
        }
    }
}