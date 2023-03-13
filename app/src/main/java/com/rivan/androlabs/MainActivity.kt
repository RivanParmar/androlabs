package com.rivan.androlabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rivan.androlabs.core.designsystem.theme.AndroLabsTheme
import com.rivan.androlabs.ui.AndroLabsApp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // TODO: Check if UI has loaded instead of using a timer for splash screen
        var disableSplashScreen = false

        Timer().schedule(object : TimerTask() {
            override fun run() {
                disableSplashScreen = true
            }
        }, 2000)

        // Currently keeps the splash screen on-screen until the above timer ends
        splashScreen.setKeepOnScreenCondition {
            disableSplashScreen
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            // TODO: Use system independent dark theme
            val darkTheme = isSystemInDarkTheme()

            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.statusBarDarkContentEnabled = !darkTheme
                onDispose {}
            }

            AndroLabsTheme(
                darkTheme = darkTheme
            ) {
                AndroLabsApp(windowSizeClass = calculateWindowSizeClass(activity = this))
            }
        }
    }
}