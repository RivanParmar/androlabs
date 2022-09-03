package com.rivan.androidplaygrounds.feature.recents.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rivan.androidplaygrounds.core.navigation.APNavigationDestination

object RecentsDestination : APNavigationDestination {
    override val route = "recents_route"
    override val destination = "recents_destination"
}

fun NavGraphBuilder.recentsGraph() {
    composable(route = RecentsDestination.route) {
        // TODO: Add composable function
    }
}
