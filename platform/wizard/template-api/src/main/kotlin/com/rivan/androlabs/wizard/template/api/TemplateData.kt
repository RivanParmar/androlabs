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

// Modified by Rivan Parmar on 06/05/2023

package com.rivan.androlabs.wizard.template.api

import java.io.File

/**
 * Data which is required for template rendering.
 *
 * One of implementations of it will be passed to the renderer (template's recipe).
 **/
sealed class TemplateData

/**
 * apiLevelString usually has the value of apiLevel (as a String), but may be a "name" for
 * unreleased APIs.
 */
data class ApiVersion(val api: Int, val apiString: String)

typealias PackageName = String
typealias GradlePluginVersion = String
typealias TemplateCategoryNames = Map<TemplateCategory, List<String>>
enum class Language(val string: String, val extension: String) {
    Java("Java", "java"),
    Kotlin("Kotlin", "kt");

    override fun toString(): String = string

    companion object {
        /**
         * Finds a language matching the requested name. Returns specified 'defaultValue' if not
         * found.
         */
        @JvmStatic
        fun fromName(name: String?, defaultValue: Language): Language =
            values().firstOrNull { it.string == name } ?: defaultValue
    }
}

enum class BuildConfigurationLanguageForNewProject(
    val description: String,
    val useKts: Boolean,
    val useVersionCatalog: Boolean
) {
    KTS("Kotlin DSL (build.gradle.kts) [Recommended]", true, false),
    KTS_VERSION_CATALOG(
        "Kotlin DSL (build.gradle.kts) + Gradle Version Catalogs [Experimental] ",
        true,
        true
    ),
    Groovy("Groovy DSL (build.gradle)", false, false);

    override fun toString() = description
}

data class ApiTemplateData(
    val buildApi: ApiVersion,
    val targetApi: ApiVersion,
    val minApi: ApiVersion,
    val appCompatVersion: Int
)

data class ProjectTemplateData(
    val androidXSupport: Boolean,
    val gradlePluginVersion: GradlePluginVersion,
    val sdkDir: File?,
    val language: Language,
    val kotlinVersion: String,
    val rootDir: File,
    val applicationPackage: PackageName?,
    val includedTemplateCategoryNames: TemplateCategoryNames,
    val isNewProject: Boolean
): TemplateData()

fun TemplateCategoryNames.has(tt: TemplateCategory) = !this[tt].isNullOrEmpty()

data class ModuleTemplateData(
    val projectTemplateData: ProjectTemplateData,
    val srcDir: File,
    val resDir: File,
    val manifestDir: File,
    val testDir: File,
    val unitTestDir: File,
    val aidlDir: File?,
    val rootDir: File,
    val isNewModule: Boolean,
    val name: String,
    val isLibrary: Boolean,
    val packageName: PackageName,
    val templateCategory: TemplateCategory,
    val themesData: ThemesData,
    val apis: ApiTemplateData,
    val viewBindingSupport: ViewBindingSupport,
    val isMaterial3: Boolean,
    val useGenericLocalTests: Boolean,
    val useGenericInstrumentedTests: Boolean
): TemplateData() {

    /**
     * Returns the [namespace](https://developer.android.com/studio/build/configure-app-module#set-namespace)
     * of the module, i.e. the package where the R and BuildConfig classes are generated.
     */
    val namespace: String
        get() = projectTemplateData.applicationPackage ?: packageName
}

/**
 * enum class representing if a module supports view binding.
 * Only supports AGP4.0+
 */
enum class ViewBindingSupport {
    NOT_SUPPORTED,
    SUPPORTED;

    fun isViewBindingSupported(): Boolean = this == SUPPORTED
}