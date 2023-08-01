package com.rivan.androlabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rivan.androlabs.MainActivityUiState.*
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.model.data.DarkThemeConfig
import com.rivan.androlabs.ui.AndrolabsApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                is Loading -> true
                is Success -> false
            }
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = shouldUseDarkTheme(uiState)

            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.statusBarDarkContentEnabled = !darkTheme
                onDispose {}
            }

            AndrolabsTheme(
                darkTheme = darkTheme
            ) {
                AndrolabsApp(
                    windowSizeClass = calculateWindowSizeClass(activity = this),
                    displayFeatures = calculateDisplayFeatures(activity = this)
                )
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState
): Boolean = when (uiState) {
    Loading -> isSystemInDarkTheme()
    is Success -> when (uiState.userSettings.darkThemeConfig) {
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
    }
}