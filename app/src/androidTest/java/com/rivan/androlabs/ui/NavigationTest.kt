package com.rivan.androlabs.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import com.rivan.androlabs.MainActivity
import com.rivan.androlabs.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.rivan.androlabs.feature.recent.R as RecentR
import com.rivan.androlabs.feature.settings.R as SettingsR

/**
 * Tests all the navigation flows that are handled by the navigation library.
 */
@HiltAndroidTest
class NavigationTest {

    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * Use the primary activity to initialize the app normally.
     */
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // The strings used for matching in these tests
    private lateinit var recent: String
    private lateinit var settings: String
    private lateinit var appName: String
    private lateinit var untitled: String

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            recent = getString(RecentR.string.recent)
            settings = getString(SettingsR.string.settings)
            appName = getString(R.string.app_name)
            untitled = getString(android.R.string.untitled)
        }
    }

    @Test
    fun firstScreen_isRecent() {
        composeTestRule.apply {
            // Verify recent is selected
            onNodeWithText(recent).assertIsSelected()
        }
    }

    @Test
    fun topLevelDestinations_showTopBarWithTitle() {
        composeTestRule.apply {
            // Verify that the top bar contains the app name on the first screen
            onNodeWithText(appName).assertExists()

            // Go to the settings tab, verify that the top app bar contains "settings". This means
            // we'll have 2 elements with the text "settings" on screen. One in the top bar, and
            // one in the bottom navigation
            onNodeWithText(settings).performClick()
            onAllNodesWithText(settings).assertCountEquals(2)
        }
    }

    @Test
    fun topLevelDestinations_showAccountIcon() {
        composeTestRule.apply {
            onNodeWithContentDescription(untitled).assertExists()

            onNodeWithText(settings).performClick()
            onNodeWithContentDescription(untitled).assertExists()
        }
    }

    @Test(expected = NoActivityResumedException::class)
    fun homeDestination_back_quitsApp() {
        composeTestRule.apply {
            // Given the user navigates to Settings destination
            onNodeWithText(settings).performClick()
            // and then navigates to Recent destination
            onNodeWithText(recent).performClick()
            // When the user uses the system button/gesture to go back
            Espresso.pressBack()
            // then the app quits
        }
    }

    /**
     * When pressing back from any top level destination except "Recent", the app navigates back
     * to the "Recent" destination, no matter which destinations you visited in between.
     */
    @Test
    fun navigationBar_backFromAnyDestination_returnsToRecent() {
        composeTestRule.apply {
            // Given user navigated to the Settings destination
            onNodeWithText(settings).performClick()
            // When the user use the system button/gesture to go back,
            Espresso.pressBack()
            // Then the app shows the Recent destination
            onNodeWithText(recent).assertExists()
        }
    }
}