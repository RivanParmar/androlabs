package com.rivan.androlabs.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.feature.home.utils.DevicePosture
import com.rivan.androlabs.feature.home.utils.isBookPosture
import com.rivan.androlabs.feature.home.utils.isSeparating

@Stable
class AndrolabsAppState(
    private val windowSizeClass: WindowSizeClass,
    val displayFeatures: List<DisplayFeature>,
    val navController: NavHostController,
) {
    private val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    private val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)
        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
        else -> DevicePosture.NormalPosture
    }

    val contentType: ContentType
        get() = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> ContentType.SINGLE_PANE
            WindowWidthSizeClass.Medium ->
                if (foldingDevicePosture != DevicePosture.NormalPosture) {
                    ContentType.DUAL_PANE
                } else {
                    ContentType.SINGLE_PANE
                }
            WindowWidthSizeClass.Expanded -> ContentType.DUAL_PANE
            else -> ContentType.SINGLE_PANE
        }
}

@Composable
fun rememberAndrolabsAppState(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    navController: NavHostController = rememberNavController(),
): AndrolabsAppState {
    return remember(navController, windowSizeClass) {
        AndrolabsAppState(windowSizeClass, displayFeatures, navController)
    }
}