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

// Modifications Copyright (C) 2023 Rivan Parmar

package com.rivan.androlabs.wizard.template.api

import java.io.File
import java.net.URL

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
typealias JavaVersion = String
typealias Revision = String
typealias FormFactorNames = Map<FormFactor, List<String>>
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
            entries.firstOrNull { it.string == name } ?: defaultValue
    }
}

const val KOTLIN_DSL_LINK = "https://d.android.com/build/migrate-to-kotlin-dsl"
enum class BuildConfigurationLanguageForNewProject(
    val description: String,
    val useKts: Boolean,
) {
    KTS("Kotlin DSL (build.gradle.kts) [Recommended]", true),
    Groovy("Groovy DSL (build.gradle)", false);

    override fun toString() = description
}

enum class BuildConfigurationLanguageForNewModule(
    val description: String,
) {
    KTS("Kotlin DSL (build.gradle.kts) [Recommended]"),
    Groovy("Groovy DSL (build.gradle)");

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
    val agpVersion: AgpVersion,
    val additionalMavenRepos: List<URL>,
    val sdkDir: File?,
    val language: Language,
    val kotlinVersion: String,
    val rootDir: File,
    val applicationPackage: PackageName?,
    val includedFormFactorNames: FormFactorNames,
    val debugKeystoreSha1: String?,
    // To disable android plugin checking for ascii paths (windows tests)
    val overridePathCheck: Boolean? = false,
    val isNewProject: Boolean,
): TemplateData()

fun FormFactorNames.has(ff: FormFactor) = !this[ff].isNullOrEmpty()

/**
 * Info about base feature.
 *
 * When we have dynamic feature project, Androlabs may need to add something to base feature
 * module even when Androlabs does not create something directly inside of the module. For
 * example, Androlabs may do it when creating a new dynamic module or an activity inside
 * dynamic module.
 */
data class BaseFeature(
    val name: String,
    val dir: File,
    val resDir: File,
)

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
    val formFactor: FormFactor,
    val themesData: ThemesData,
    /**
     * Info about base feature. Only present in dynamic feature project.
     */
    val baseFeature: BaseFeature?,
    val apis: ApiTemplateData,
    val viewBindingSupport: ViewBindingSupport,
    val category: Category,
    val isCompose: Boolean,
    val isMaterial3: Boolean,
    val useGenericLocalTests: Boolean,
    val useGenericInstrumentedTests: Boolean,
): TemplateData() {
    val isDynamic: Boolean
        get() = baseFeature != null

    /**
     * Returns the
     * [namespace](https://developer.android.com/studio/build/configure-app-module#set-namespace)
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