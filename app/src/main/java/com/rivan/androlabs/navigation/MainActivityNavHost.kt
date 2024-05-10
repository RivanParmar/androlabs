package com.rivan.androlabs.navigation

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.rivan.androlabs.feature.home.navigation.homeNavigationRoute
import com.rivan.androlabs.feature.home.navigation.homeScreen
import com.rivan.androlabs.feature.nlw.navigation.navigateToNlw
import com.rivan.androlabs.feature.nlw.navigation.nlwScreen
import com.rivan.androlabs.feature.settings.navigation.navigateToSettingsGraph
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
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
    askToSelectSavePath: Boolean = false,
    context: Context = LocalContext.current,
    updateSavePath: (String) -> Unit,
) {
    // TODO: Handle use case when user does not select anything
    //  In such a case we could either show an AlertDialog or a Snackbar
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) {
        if (it != null) {
            context.contentResolver.takePersistableUriPermission(
                it,
                (Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            )
            // TODO: We're currently saving the URI. Instead we may need to only save the path.
            updateSavePath(it.toString())
        }
    }

    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            contentType = appState.contentType,
            listType = appState.listType,
            displayFeatures = appState.displayFeatures,
            onSearchBarTrailingIconClick = {
                // TODO: This is only for now until the UI has been completed
                navController.navigateToSettingsGraph()
            },
            onFABClick = {
                // Ask the user to select a common path for saving the labs
                if (askToSelectSavePath) {
                    launcher.launch(null)
                } else {
                    navController.navigateToNlw()
                }
            },
        )

        nlwScreen()

        settingsScreen(appState.contentType, appState.displayFeatures) { settingsCategoryRoute ->
            navController.navigate(settingsCategoryRoute)
        }
    }
}