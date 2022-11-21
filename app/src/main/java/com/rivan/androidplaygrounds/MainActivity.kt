package com.rivan.androidplaygrounds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import com.rivan.androidplaygrounds.ui.AndroidPlaygroundsApp
import com.rivan.androidplaygrounds.ui.theme.AndroidPlaygroundsTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // TODO: Use system independent dark theme
            val darkTheme = isSystemInDarkTheme()
            AndroidPlaygroundsTheme(
                darkTheme = darkTheme
            ) {
                AndroidPlaygroundsApp(windowSizeClass = calculateWindowSizeClass(activity = this))
            }
        }
    }
}