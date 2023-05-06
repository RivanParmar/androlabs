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

/**
 * Information about an Android theme.
 */
data class ThemeData(
    val name: String,
    val exists: Boolean
)

/**
 * Information about project themes.
 */
data class ThemesData(
    val appName: String,
    val main: ThemeData = ThemeData("Theme.$appName", false),
    val overlay: ThemeData = ThemeData("ThemeOverlay.$appName", false),
    val noActionBar: ThemeData = ThemeData("Theme.$appName.NoActionBar", false),
    val appBarOverlay: ThemeData = ThemeData("Theme.$appName.AppBarOverlay", false),
    val popupOverlay: ThemeData = ThemeData("Theme.$appName.PopupOverlay", false)
)