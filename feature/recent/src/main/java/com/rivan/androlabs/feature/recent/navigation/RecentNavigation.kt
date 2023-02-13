package com.rivan.androlabs.feature.recent.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rivan.androlabs.feature.recent.RecentRoute

const val recentNavigationRoute = "recent_route"

fun NavController.navigateToRecent(navOptions: NavOptions? = null) {
    this.navigate(recentNavigationRoute, navOptions)
}

fun NavGraphBuilder.recentScreen() {
    composable(route = recentNavigationRoute) {
        RecentRoute()
    }
}