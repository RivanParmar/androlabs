package com.rivan.androidplaygrounds.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.rivan.androidplaygrounds.feature.recent.navigation.navigateToRecent
import com.rivan.androidplaygrounds.feature.recent.navigation.recentNavigationRoute
import com.rivan.androidplaygrounds.feature.settings.navigation.navigateToSettings
import com.rivan.androidplaygrounds.feature.settings.navigation.settingsNavigationRoute
import com.rivan.androidplaygrounds.navigation.TopLevelDestination
import com.rivan.androidplaygrounds.navigation.TopLevelDestination.RECENT
import com.rivan.androidplaygrounds.navigation.TopLevelDestination.SETTINGS

@Stable
class AndroidPlaygroundsAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            recentNavigationRoute -> RECENT
            settingsNavigationRoute -> SETTINGS
            else -> null
        }

    /**
     * Show Bottom Navigation Bar instead of Navigation Rail on small screen devices.
     */
    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
            windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    /**
     * Show Navigation Rail on large screen devices.
     */
    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            RECENT -> navController.navigateToRecent(topLevelNavOptions)
            SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
        }
    }

    fun onBackClick() = navController.popBackStack()
}

@Composable
fun rememberAndroidPlaygroundsAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): AndroidPlaygroundsAppState {
    return remember(navController, windowSizeClass) {
        AndroidPlaygroundsAppState(navController, windowSizeClass)
    }
}