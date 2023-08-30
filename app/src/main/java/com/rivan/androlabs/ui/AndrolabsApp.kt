package com.rivan.androlabs.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.window.layout.DisplayFeature
import com.rivan.androlabs.navigation.MainActivityNavHost

@Composable
fun AndrolabsApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    appState: AndrolabsAppState = rememberAndrolabsAppState(
        windowSizeClass = windowSizeClass,
        displayFeatures = displayFeatures
    ),
    askToSelectSavePath: Boolean,
    updateSavePath: (String) -> Unit,
) {
    MainActivityNavHost(
        appState = appState,
        navController = appState.navController,
        askToSelectSavePath = askToSelectSavePath,
        updateSavePath = updateSavePath,
    )
}