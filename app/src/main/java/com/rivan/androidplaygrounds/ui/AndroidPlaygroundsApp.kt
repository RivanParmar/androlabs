package com.rivan.androidplaygrounds.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.rivan.androidplaygrounds.core.designsystem.component.APBackground

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)

@Composable
fun AndroidPlaygroundsApp(
    windowSizeClass: WindowSizeClass,
    appState: AndroidPlaygroundsAppState = rememberAndroidPlaygroundsAppState(
        windowSizeClass = windowSizeClass
    )
) {
    val background: @Composable (@Composable () -> Unit) -> Unit = {
        content ->
        APBackground(content = content)
    }

    background {

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                // Show the top app bar on top level destinations
                val destination = appState.currentTopLevelDestination
                if (destination != null) {

                }
            }
        ) {
            // This is just a sample composable to suppress warnings
            // TODO: Remove once UI elements are ready
            Text(text = "Hello, world!", modifier = Modifier.padding(it))
        }
    }
}