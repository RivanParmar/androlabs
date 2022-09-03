package com.rivan.androidplaygrounds.navigation

import androidx.navigation.NavController

class APTopLevelDestinations(private val navController: NavController) {

}

data class TopLevelDestination(
    val route: String,
    val iconTextId: Int
)