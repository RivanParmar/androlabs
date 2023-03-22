package com.rivan.androlabs.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.accompanist.testharness.TestHarness
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests that the navigation UI is rendered correctly on different screen sizes.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@HiltAndroidTest
class NavigationUiTest {

    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * Use a test activity to set the content on.
     */
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun compactWidth_compactHeight_showsNavigationBar() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(400.dp, 400.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALBottomBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALNavRail").assertDoesNotExist()
    }

    @Test
    fun mediumWidth_compactHeight_showsNavigationRail() {
        composeTestRule.setContent { 
            TestHarness(size = DpSize(610.dp, 400.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALNavRail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALBottomBar").assertDoesNotExist()
    }

    @Test
    fun expandedWidth_compactHeight_showsNavigationRail() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(900.dp, 400.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALNavRail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALBottomBar").assertDoesNotExist()
    }

    @Test
    fun compactWidth_mediumHeight_showsNavigationBar() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(400.dp, 500.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALBottomBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALNavRail").assertDoesNotExist()
    }

    @Test
    fun mediumWidth_mediumHeight_showsNavigationRail() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(610.dp, 500.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALNavRail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALBottomBar").assertDoesNotExist()
    }

    @Test
    fun expandedWidth_mediumHeight_showsNavigationRail() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(900.dp, 500.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALNavRail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALBottomBar").assertDoesNotExist()
    }

    @Test
    fun compactWidth_expandedHeight_showsNavigationBar() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(400.dp, 1000.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALBottomBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALNavRail").assertDoesNotExist()
    }

    @Test
    fun mediumWidth_expandedHeight_showsNavigationRail() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(610.dp, 1000.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALNavRail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALBottomBar").assertDoesNotExist()
    }

    @Test
    fun expandedWidth_expandedHeight_showsNavigationRail() {
        composeTestRule.setContent {
            TestHarness(size = DpSize(900.dp, 1000.dp)) {
                BoxWithConstraints {
                    AndroLabsApp(
                        windowSizeClass = WindowSizeClass.calculateFromSize(
                            DpSize(maxWidth, maxHeight)
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("ALNavRail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ALBottomBar").assertDoesNotExist()
    }
}