package com.rivan.androlabs.ui

import androidx.compose.runtime.Composable
import com.rivan.androlabs.navigation.MainActivityNavHost

@Composable
fun AndrolabsApp(
    appState: AndrolabsAppState,
    askToSelectSavePath: Boolean,
    updateSavePath: (String) -> Unit,
) {
    MainActivityNavHost(
        appState = appState,
        askToSelectSavePath = askToSelectSavePath,
        updateSavePath = updateSavePath,
    )
}