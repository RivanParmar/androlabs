package com.rivan.androidplaygrounds.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rivan.androidplaygrounds.feature.recent.navigation.recentNavigationRoute
import com.rivan.androidplaygrounds.feature.recent.navigation.recentScreen
import com.rivan.androidplaygrounds.feature.settings.navigation.settingScreen

@Composable
fun APNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = recentNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        recentScreen()
        settingScreen()
    }
}