package com.rivan.androidplaygrounds.feature.recent.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rivan.androidplaygrounds.feature.recent.RecentRoute
import com.rivan.androidplaygrounds.feature.recent.RecentScreen

const val recentNavigationRoute = "recent_route"

fun NavController.navigateToRecent(navOptions: NavOptions? = null) {
    this.navigate(recentNavigationRoute, navOptions)
}

fun NavGraphBuilder.recentScreen() {
    composable(route = recentNavigationRoute) {
        RecentRoute()
    }
}