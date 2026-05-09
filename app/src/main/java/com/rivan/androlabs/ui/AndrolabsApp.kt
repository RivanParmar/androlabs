package com.rivan.androlabs.ui

import androidx.compose.runtime.Composable
import com.rivan.androlabs.navigation.MainActivityNavHost

@Composable
fun AndrolabsApp(
    appState: AndrolabsAppState,
    defaultPath: String?,
    askToSelectDefaultPath: Boolean,
    updateDefaultPath: (String) -> Unit,
    openProject: (String) -> Unit,
) {
    MainActivityNavHost(
        appState = appState,
        defaultPath = defaultPath,
        askToSelectDefaultPath = askToSelectDefaultPath,
        updateDefaultPath = updateDefaultPath,
        openProject = openProject,
    )
}