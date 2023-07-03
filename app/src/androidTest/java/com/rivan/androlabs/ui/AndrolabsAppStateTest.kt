package com.rivan.androlabs.ui

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class AndrolabsAppStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Subject under test
    private lateinit var state: AndrolabsAppState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun androLabsAppState_currentDestination() = runTest {
        var currentDestination: String? = null

        composeTestRule.setContent {
            val navController = rememberTestNavController()
            state = remember(navController) {
                AndrolabsAppState(
                    windowSizeClass = getCompactWindowClass(),
                    navController = navController
                )
            }

            // Update currentDestination whenever it changes
            currentDestination = state.currentDestination?.route

            // Navigate to destination b once
            LaunchedEffect(Unit) {
                navController.setCurrentDestination("b")
            }
        }

        assertEquals("b", currentDestination)
    }

    @Test
    fun androLabsAppState_destination() = runTest {
        composeTestRule.setContent {
            state = rememberAndrolabsAppState(
                windowSizeClass = getCompactWindowClass()
            )
        }

        assertEquals(2, state.topLevelDestinations.size)
        assertTrue(state.topLevelDestinations[0].name.contains("recent", true))
        assertTrue(state.topLevelDestinations[1].name.contains("settings", true))
    }

    @Test
    fun androLabsAppState_showBottomBar_compact() = runTest {
        composeTestRule.setContent {
            state = AndrolabsAppState(
                windowSizeClass = getCompactWindowClass(),
                navController = NavHostController(LocalContext.current)
            )
        }

        assertTrue(state.shouldShowBottomBar)
        assertFalse(state.shouldShowNavRail)
    }

    @Test
    fun androLabsAppState_showNavRail_medium() = runTest {
        composeTestRule.setContent {
            state = AndrolabsAppState(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(800.dp, 800.dp)),
                navController = NavHostController(LocalContext.current)
            )
        }

        assertTrue(state.shouldShowNavRail)
        assertFalse(state.shouldShowBottomBar)
    }

    @Test
    fun androLabsAppState_showNavRail_large() = runTest {

        composeTestRule.setContent {
            state = AndrolabsAppState(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(900.dp, 1200.dp)),
                navController = NavHostController(LocalContext.current)
            )
        }

        assertTrue(state.shouldShowNavRail)
        assertFalse(state.shouldShowBottomBar)
    }


    private fun getCompactWindowClass() = WindowSizeClass.calculateFromSize(DpSize(500.dp, 300.dp))
}

@Composable
private fun rememberTestNavController(): TestNavHostController {
    val context = LocalContext.current
    val navController = remember {
        TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            graph = createGraph(startDestination = "a") {
                composable("a") { }
                composable("b") { }
            }
        }
    }
    return navController
}