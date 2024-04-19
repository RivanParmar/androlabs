/*
 * Copyright 2023 Rivan Parmar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.window.layout.DisplayFeature
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.feature.editor.navigation.editorNavigationRoute
import com.rivan.androlabs.feature.editor.navigation.editorScreen
import com.rivan.androlabs.feature.settings.navigation.settingsScreen

@Composable
fun EditorActivityNavHost(
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = editorNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        editorScreen()

        settingsScreen(contentType, displayFeatures) { settingsCategoryRoute ->
            navController.navigate(settingsCategoryRoute)
        }
    }
}