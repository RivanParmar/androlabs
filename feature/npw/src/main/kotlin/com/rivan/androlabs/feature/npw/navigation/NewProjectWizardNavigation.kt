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

package com.rivan.androlabs.feature.npw.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.rivan.androlabs.feature.npw.NewProjectWizardRoute

private const val newProjectWizardNavigationRoute = "new_project_wizard_route"

fun NavController.navigateToNewProjectWizard(navOptions: NavOptions? = null) {
    this.navigate(newProjectWizardNavigationRoute, navOptions)
}

fun NavGraphBuilder.newProjectWizardScreen(
    onBackClick: () -> Unit
) {
    composable(route = newProjectWizardNavigationRoute) {
        NewProjectWizardRoute(onBackClick = onBackClick)
    }
}