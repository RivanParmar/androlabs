package com.rivan.androidplaygrounds.core.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// TODO: Not yet completed
/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightDefaultColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    secondary = PurpleGrey40,
    onSecondary = Color.White,
    tertiary = Pink40,
    onTertiary = Color.White
)

// TODO: Not yet completed
/**
 * Dark default theme color scheme
 */
@VisibleForTesting
val DarkDefaultColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Color.White,
    secondary = PurpleGrey80,
    onSecondary = Color.White,
    tertiary = Pink80,
    onTertiary = Color.White
)

/**
 * Android Playgrounds theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param dynamicColor If `true`, then dynamic theming will be used when supported.
 */
@Composable
fun AndroidPlaygroundsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }
    /*val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }*/

    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp
    )

    // Composition locals
    CompositionLocalProvider(
        LocalBackgroundTheme provides defaultBackgroundTheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S