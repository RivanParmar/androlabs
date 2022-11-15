package com.rivan.androidplaygrounds.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable

@Composable
fun AndroidPlaygroundsApp(
    windowSizeClass: WindowSizeClass,
    appState: AndroidPlaygroundsAppState = rememberAndroidPlaygroundsAppState(
        windowSizeClass = windowSizeClass
    )
) {

}