package com.rivan.androlabs.ui

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
import com.rivan.androlabs.feature.recent.navigation.navigateToRecent
import com.rivan.androlabs.feature.recent.navigation.recentNavigationRoute
import com.rivan.androlabs.feature.settings.navigation.navigateToSettings
import com.rivan.androlabs.feature.settings.navigation.settingsNavigationRoute
import com.rivan.androlabs.navigation.TopLevelDestination
import com.rivan.androlabs.navigation.TopLevelDestination.*

@Stable
class AndroLabsAppState(
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
    val topLevelDestinations: List<TopLevelDestination> = values().asList()

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
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
fun rememberAndroLabsAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): AndroLabsAppState {
    return remember(navController, windowSizeClass) {
        AndroLabsAppState(navController, windowSizeClass)
    }
}