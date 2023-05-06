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

fun RecipeExecutor.addAllKotlinDependency(
    data: ModuleTemplateData,
    revision: String = data.projectTemplateData.kotlinVersion
) {
    val projectData = data.projectTemplateData
    if (!data.isNewModule && projectData.language == Language.Kotlin) {
        applyPlugin("org.jetbrains.kotlin.android", revision)
        addClasspathDependency("org.jetbrains.kotlin:kotlin-gradle-plugin:$revision")
    }
}

fun RecipeExecutor.addMaterial3Dependency() {
    addDependency("com.google.android.material:material:+", minRev = "1.5.0")
}