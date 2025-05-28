package com.rivan.androlabs.ui

import androidx.compose.runtime.Composable
import com.rivan.androlabs.navigation.MainActivityNavHost

@Composable
fun AndrolabsApp(
    appState: AndrolabsAppState,
    savePath: String?,
    askToSelectSavePath: Boolean,
    updateSavePath: (String) -> Unit,
    openProject: (String) -> Unit,
) {
    MainActivityNavHost(
        appState = appState,
        savePath = savePath,
        askToSelectSavePath = askToSelectSavePath,
        updateSavePath = updateSavePath,
        openProject = openProject,
    )
}