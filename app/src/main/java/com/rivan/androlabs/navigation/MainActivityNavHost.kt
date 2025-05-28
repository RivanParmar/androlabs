package com.rivan.androlabs.navigation

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.rivan.androlabs.EditorActivity
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
    savePath: String?,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
    askToSelectSavePath: Boolean = false,
    context: Context = LocalContext.current,
    updateSavePath: (String) -> Unit,
    openProject: (String) -> Unit,
) {
    // TODO: Handle use case when user does not select anything
    //  In such a case we could either show an AlertDialog or a Snackbar
    val savePathSelector = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
    ) {
        if (it != null) {
            context.contentResolver.takePersistableUriPermission(
                it,
                (Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION),
            )
            updateSavePath(it.toString())
        }
    }

    val projectOpener = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
    ) {
        if (it != null && savePath != null) {
            // TODO: This doesn't work! We will need to create a proper class for
            //  handling the paths and permissions
            if (!savePath.contains(it.toString())) {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    (Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION),
                )
            }
            openProject(it.toString())
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
            onSearchBarTrailingIconClick = {
                // TODO: This is only for now until the UI has been completed
                navController.navigateToSettingsGraph()
            },
            onLabItemClick = {
                context.startActivity(Intent(context, EditorActivity::class.java))
            },
            onFABClick = { item ->
                // TODO: Show a dialog before opening the folder picker.

                // Ask the user to select a common path for saving the labs
                if (askToSelectSavePath) {
                    savePathSelector.launch(null)
                } else {
                    when (item) {
                        "Open" -> projectOpener.launch(null)
                        "New" -> navController.navigateToNlw()
                        else -> { /* Do nothing */ }
                    }
                }
            },
        )

        nlwScreen()

        settingsScreen(appState.contentType, appState.displayFeatures) { settingsCategoryRoute ->
            navController.navigate(settingsCategoryRoute)
        }
    }
}
