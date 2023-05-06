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

package com.rivan.androlabs.wizard.template.impl.activities.emptyActivity

import com.rivan.androlabs.wizard.template.api.Language
import com.rivan.androlabs.wizard.template.api.ModuleTemplateData
import com.rivan.androlabs.wizard.template.api.PackageName
import com.rivan.androlabs.wizard.template.api.RecipeExecutor
import com.rivan.androlabs.wizard.template.impl.activities.common.addAllKotlinDependency
import com.rivan.androlabs.wizard.template.impl.activities.common.addMaterial3Dependency
import com.rivan.androlabs.wizard.template.impl.activities.common.generateManifest
import com.rivan.androlabs.wizard.template.impl.activities.common.generateSimpleLayout
import com.rivan.androlabs.wizard.template.impl.activities.emptyActivity.src.emptyActivityJava
import com.rivan.androlabs.wizard.template.impl.activities.emptyActivity.src.emptyActivityKt

fun RecipeExecutor.generateEmptyActivity(
    moduleData: ModuleTemplateData,
    activityClass: String,
    generateLayout: Boolean,
    layoutName: String,
    isLauncher: Boolean,
    packageName: PackageName
) {
    val (projectData, srcOut) = moduleData
    val useAndroidX = projectData.androidXSupport
    val ktOrJavaExt = projectData.language.extension

    addDependency("com.android.support:appcompat-v7:${moduleData.apis.appCompatVersion}.+")
    addMaterial3Dependency()

    generateManifest(
        moduleData, activityClass, packageName, isLauncher, false,
        generateActivityTitle = false
    )

    addAllKotlinDependency(moduleData)

    if (generateLayout) {
        generateSimpleLayout(moduleData, activityClass, layoutName)
    }

    val simpleActivity = when (projectData.language) {
        Language.Kotlin ->
            emptyActivityKt(packageName, moduleData.namespace, activityClass,
                layoutName, generateLayout, useAndroidX)
        Language.Java ->
            emptyActivityJava(packageName, moduleData.namespace, activityClass,
                layoutName, generateLayout, useAndroidX)
    }

    val simpleActivityPath = srcOut.resolve("$activityClass.$ktOrJavaExt")
    save(simpleActivity, simpleActivityPath)
    open(simpleActivityPath)
}