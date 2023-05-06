/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.wizard.template.api

import com.google.common.base.CaseFormat
import com.google.common.io.Resources
import com.rivan.androlabs.wizard.template.api.AssetNameConverter.Type
import java.io.File
import java.net.URL

/** Converts an Activity class name into a suitable layout name. */
fun activityToLayout(activityName: String, layoutName: String? = null): String =
    if (activityName.isNotEmpty())
        AssetNameConverter(Type.ACTIVITY, activityName)
            .overrideLayoutPrefix(layoutName)
            .getValue(Type.LAYOUT)
    else
        ""

/** Converts a Fragment class name into a suitable layout name. */
fun fragmentToLayout(fragmentName: String, layoutName: String? = null): String =
    if (fragmentName.isNotEmpty())
        AssetNameConverter(Type.FRAGMENT, fragmentName)
            .overrideLayoutPrefix(layoutName ?: "fragment")
            .getValue(Type.LAYOUT)
    else
        ""

/**
 * Similar to [camelCaseToUnderlines], but strips off common class suffixes such as "Activity",
 * "Fragment", etc.
 */
fun classToResource(name: String): String =
    if (name.isNotEmpty())
        AssetNameConverter(Type.CLASS_NAME, name).getValue(Type.RESOURCE)
    else ""

/**
 * Converts a String to Camel Case. The return will not contain any two consecutive upper case
 * characters
 * For example:
 * MyCLASsName to MyClassName
 * my_class_name to MyClassName
 * URL to Url
 **/
fun toUpperCamelCase(string: String): String {
    if (string.length <= 1) {
        return string
    }

    return buildString(string.length) {
        var previous = ' '
        string.forEach { c ->
            when {
                c == '_' -> Unit
                isEmpty() -> append(c.uppercaseChar())
                last().isUpperCase() -> append(c.lowercaseChar())
                previous == '_' -> append(c.uppercaseChar())
                previous.isUpperCase() -> append(c.lowercaseChar())
                else -> append(c)
            }
            previous = c
        }
    }
}

fun camelCaseToUnderlines(string: String): String =
    CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string)

fun underscoreToCamelCase(
    string: String
): String = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, string)

fun underscoreToLowerCamelCase(
    string: String
): String = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, string)

fun escapeKotlinIdentifier(identifier: String): String =
    identifier.split(".").joinToString(".") {
        if (it in kotlinKeywords) "`$it`" else it
    }

/**
 * Creates a Java class name out of the given string, if possible.
 * For example, "My Project" becomes "MyProject", "hello" becomes "Hello", "Java's" becomes "Javas",
 * and so on.
 *
 * @param string the string to be massaged into a Java class
 * @return the string as a Java class, or null if a class name could not be extracted
 */
fun extractClassName(string: String): String? {
    val javaIdentifier = string.dropWhile {
        !Character.isJavaIdentifierStart(it.uppercaseChar())
    }.filter(Character::isJavaIdentifierPart)
    if (javaIdentifier.isBlank()) {
        return null
    }
    return javaIdentifier.first().uppercaseChar() + javaIdentifier.substring(1)
}

fun layoutToActivity(name: String): String =
    AssetNameConverter(Type.LAYOUT, name).getValue(Type.ACTIVITY)
fun layoutToFragment(name: String): String =
    AssetNameConverter(Type.LAYOUT, name).getValue(Type.FRAGMENT)

fun getMaterialComponentName(mavenCoordinate: String, useAndroidX: Boolean): String {
    if (!useAndroidX) {
        return mavenCoordinate
    }
    val mapping = mapOf(
        "android.arch.core.util.Function" to "androidx.arch.core.util.Function",
        "android.arch.lifecycle.LiveData" to "androidx.lifecycle.LiveData",
        "android.arch.lifecycle.MutableLiveData" to "androidx.lifecycle.MutableLiveData",
        "android.arch.lifecycle.Observer" to "androidx.lifecycle.Observer",
        "android.arch.lifecycle.Transformations" to "androidx.lifecycle.Transformations",
        "android.arch.lifecycle.ViewModel" to "androidx.lifecycle.ViewModel",
        "android.arch.lifecycle.ViewModelProvider" to "androidx.lifecycle.ViewModelProvider",
        "android.support.annotation.NonNull" to "androidx.annotation.NonNull",
        "android.support.annotation.Nullable" to "androidx.annotation.Nullable",
        "android.support.annotation.StringRes" to "androidx.annotation.StringRes",
        "android.support.constraint.ConstraintLayout" to
                "androidx.constraintlayout.widget.ConstraintLayout",
        "android.support.constraint.Guideline" to "androidx.constraintlayout.widget.Guideline",
        "android.support.design.widget.AppBarLayout" to
                "com.google.android.material.appbar.AppBarLayout",
        "android.support.design.widget.BottomNavigationView" to
                "com.google.android.material.bottomnavigation.BottomNavigationView",
        "android.support.design.widget.BottomSheetDialog" to
                "com.google.android.material.bottomsheet.BottomSheetDialog",
        "android.support.design.widget.CollapsingToolbarLayout" to
                "com.google.android.material.appbar.CollapsingToolbarLayout",
        "android.support.design.widget.CoordinatorLayout" to
                "androidx.coordinatorlayout.widget.CoordinatorLayout",
        "android.support.design.widget.FloatingActionButton" to
                "com.google.android.material.floatingactionbutton.FloatingActionButton",
        "android.support.design.widget.NavigationView" to
                "com.google.android.material.navigation.NavigationView",
        "android.support.design.widget.Snackbar" to "com.google.android.material.snackbar.Snackbar",
        "android.support.design.widget.TabLayout" to "com.google.android.material.tabs.TabLayout",
        "android.support.design.widget.TabLayout" to "com.google.android.material.tabs.TabLayout",
        "android.support.test.InstrumentationRegistry" to
                "androidx.test.platform.app.InstrumentationRegistry",
        "android.support.test.runner.AndroidJUnit4" to
                "androidx.test.ext.junit.runners.AndroidJUnit4",
        "android.support.test.runner.AndroidJUnitRunner" to
                "androidx.test.runner.AndroidJUnitRunner",
        "android.support.v17.leanback.app.BackgroundManager" to
                "androidx.leanback.app.BackgroundManager",
        "android.support.v17.leanback.app.BrowseSupportFragment" to
                "androidx.leanback.app.BrowseSupportFragment",
        "android.support.v17.leanback.app.DetailsSupportFragment" to
                "androidx.leanback.app.DetailsSupportFragment",
        "android.support.v17.leanback.app.DetailsSupportFragmentBackgroundController" to
                "androidx.leanback.app.DetailsSupportFragmentBackgroundController",
        "android.support.v17.leanback.app.ErrorSupportFragment" to
                "androidx.leanback.app.ErrorSupportFragment",
        "android.support.v17.leanback.app.VideoSupportFragment" to
                "androidx.leanback.app.VideoSupportFragment",
        "android.support.v17.leanback.app.VideoSupportFragmentGlueHost" to
                "androidx.leanback.app.VideoSupportFragmentGlueHost",
        "android.support.v17.leanback.media.MediaPlayerAdapter" to
                "androidx.leanback.media.MediaPlayerAdapter",
        "android.support.v17.leanback.media.PlaybackTransportControlGlue" to
                "androidx.leanback.media.PlaybackTransportControlGlue",
        "android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter" to
                "androidx.leanback.widget.AbstractDetailsDescriptionPresenter",
        "android.support.v17.leanback.widget.Action" to "androidx.leanback.widget.Action",
        "android.support.v17.leanback.widget.ArrayObjectAdapter" to
                "androidx.leanback.widget.ArrayObjectAdapter",
        "android.support.v17.leanback.widget.ClassPresenterSelector" to
                "androidx.leanback.widget.ClassPresenterSelector",
        "android.support.v17.leanback.widget.DetailsOverviewRow" to
                "androidx.leanback.widget.DetailsOverviewRow",
        "android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter" to
                "androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter",
        "android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper" to
                "androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper",
        "android.support.v17.leanback.widget.HeaderItem" to "androidx.leanback.widget.HeaderItem",
        "android.support.v17.leanback.widget.ImageCardView" to
                "androidx.leanback.widget.ImageCardView",
        "android.support.v17.leanback.widget.ListRow" to "androidx.leanback.widget.ListRow",
        "android.support.v17.leanback.widget.ListRowPresenter" to
                "androidx.leanback.widget.ListRowPresenter",
        "android.support.v17.leanback.widget.OnActionClickedListener" to
                "androidx.leanback.widget.OnActionClickedListener",
        "android.support.v17.leanback.widget.OnItemViewClickedListener" to
                "androidx.leanback.widget.OnItemViewClickedListener",
        "android.support.v17.leanback.widget.OnItemViewSelectedListener" to
                "androidx.leanback.widget.OnItemViewSelectedListener",
        "android.support.v17.leanback.widget.PlaybackControlsRow" to
                "androidx.leanback.widget.PlaybackControlsRow",
        "android.support.v17.leanback.widget.Presenter" to "androidx.leanback.widget.Presenter",
        "android.support.v17.leanback.widget.Row" to "androidx.leanback.widget.Row",
        "android.support.v17.leanback.widget.RowPresenter" to
                "androidx.leanback.widget.RowPresenter",
        "android.support.v4.app.ActivityOptionsCompat" to "androidx.core.app.ActivityOptionsCompat",
        "android.support.v4.app.Fragment" to "androidx.fragment.app.Fragment",
        "android.support.v4.app.FragmentActivity" to "androidx.fragment.app.FragmentActivity",
        "android.support.v4.app.FragmentManager" to "androidx.fragment.app.FragmentManager",
        "android.support.v4.app.FragmentPagerAdapter" to
                "androidx.fragment.app.FragmentPagerAdapter",
        "android.support.v4.app.NotificationCompat" to "androidx.core.app.NotificationCompat",
        "android.support.v4.app.NotificationCompat.CarExtender" to
                "androidx.core.app.NotificationCompat.CarExtender",
        "android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation" to
                "androidx.core.app.NotificationCompat.CarExtender.UnreadConversation",
        "android.support.v4.app.NotificationManagerCompat" to
                "androidx.core.app.NotificationManagerCompat",
        "android.support.v4.app.RemoteInput" to "androidx.core.app.RemoteInput",
        "android.support.v4.content.ContextCompat" to "androidx.core.content.ContextCompat",
        "android.support.v4.media.MediaBrowserServiceCompat" to
                "androidx.media.MediaBrowserServiceCompat",
        "android.support.v4.view.ViewPager" to "androidx.viewpager.widget.ViewPager",
        "android.support.v4.widget.DrawerLayout" to "androidx.drawerlayout.widget.DrawerLayout",
        "android.support.v4.widget.NestedScrollView" to "androidx.core.widget.NestedScrollView",
        "android.support.v7.app.ActionBar" to "androidx.appcompat.app.ActionBar",
        "android.support.v7.app.AppCompatActivity" to "androidx.appcompat.app.AppCompatActivity",
        "android.support.v7.graphics.Palette" to "androidx.palette.graphics.Palette",
        "android.support.v7.widget.GridLayoutManager" to
                "androidx.recyclerview.widget.GridLayoutManager",
        "android.support.v7.widget.LinearLayoutManager" to
                "androidx.recyclerview.widget.LinearLayoutManager",
        "android.support.v7.widget.RecyclerView" to "androidx.recyclerview.widget.RecyclerView",
        "android.support.v7.widget.Toolbar" to "androidx.appcompat.widget.Toolbar",
        "android.support.wear.widget.BoxInsetLayout" to "androidx.wear.widget.BoxInsetLayout",
        "android.support.wear.widget.SwipeDismissFrameLayout" to
                "androidx.wear.widget.SwipeDismissFrameLayout"
    )

    val androidXCoordinate = mapping[mavenCoordinate]

    require(androidXCoordinate != null) {
        """
            Unknown dependency.
            It is recommended to avoid this method in favor of using androidX dependencies directly.
        """.trimIndent()
    }

    return androidXCoordinate
}

// From https://github.com/JetBrains/kotlin/blob/master/core/descriptors/src/org/jetbrains/kotlin/renderer/KeywordStringsGenerated.java
private val kotlinKeywords = listOf(
    "package", "as", "typealias", "class", "this", "super", "val", "var", "fun", "for", "null",
    "true", "false", "is", "in", "throw", "return", "break", "continue", "object", "if", "try",
    "else", "while", "do", "when", "interface", "typeof")

/** Finds a resource given a file path and a context class. */
fun findResource(contextClass: Class<Any>, from: File) : URL {
    return Resources.getResource(contextClass, "/${from.path}")
}