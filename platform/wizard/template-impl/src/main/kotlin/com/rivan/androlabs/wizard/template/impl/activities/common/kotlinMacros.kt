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

package com.rivan.androlabs.wizard.template.impl.activities.common

import com.rivan.androlabs.wizard.template.api.Language
import com.rivan.androlabs.wizard.template.api.ModuleTemplateData
import com.rivan.androlabs.wizard.template.api.RecipeExecutor

fun RecipeExecutor.addAllKotlinDependencies(
    data: ModuleTemplateData,
    revision: String = data.projectTemplateData.kotlinVersion,
) {
    val projectData = data.projectTemplateData
    if (!data.isNewModule && projectData.language == Language.Kotlin) {
        applyPlugin(
            "org.jetbrains.kotlin.android",
            "org.jetbrains.kotlin:kotlin-gradle-plugin",
            revision,
        )
    }
}

fun RecipeExecutor.addComposeDependencies(
    data: ModuleTemplateData,
    composeBomVersion: String = COMPOSE_BOM_VERSION,
    composeUiVersion: String? = null,
) {
    addPlugin(
        "org.jetbrains.kotlin.plugin.compose",
        "org.jetbrains.kotlin:compose-compiler-gradle-plugin",
        data.projectTemplateData.kotlinVersion,
    )
    addPlatformDependency(mavenCoordinate = "androidx.compose:compose-bom:$composeBomVersion")
    addPlatformDependency(
        mavenCoordinate = "androidx.compose:compose-bom:$composeBomVersion",
        configuration = "androidTestImplementation",
    )

    val composeUiFormattedVersion = composeUiVersion?.let { ":$it" } ?: ""
    addDependency(mavenCoordinate = "androidx.compose.ui:ui$composeUiFormattedVersion")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-graphics")
    addDependency(
        mavenCoordinate = "androidx.compose.ui:ui-tooling",
        configuration = "debugImplementation",
    )
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-tooling-preview")
    addDependency(
        mavenCoordinate = "androidx.compose.ui:ui-test-manifest",
        configuration = "debugImplementation",
    )
    addDependency(
        mavenCoordinate = "androidx.compose.ui:ui-test-junit4",
        configuration = "androidTestImplementation",
    )
}

fun RecipeExecutor.addMaterialDependency(useAndroidX: Boolean) {
    if (useAndroidX) {
        // Material 2 dependencies are now pinned to 1.4.0
        addDependency("com.google.android.material:material:1.4.+")
    }
}

fun RecipeExecutor.addMaterial3Dependency() {
    addDependency("com.google.android.material:material:+", minRev = "1.5.0")
}

fun RecipeExecutor.addSupportWearableDependency() {
    addDependency("com.google.android.support:wearable:+", minRev = "2.8.1")
    // This is needed for the com.google.android.support:wearable as a provided dependency
    // otherwise it's warned by lint
    addDependency("com.google.android.wearable:wearable:+", "provided", minRev = "2.8.1")
}