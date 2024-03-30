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

/** Execution engine for the instructions in a recipe. */
interface RecipeExecutor {
    /**
     * Copies the given source file into the given destination file (where the
     * source is allowed to be a directory, in which case the whole directory is copied recursively).
     */
    fun copy(from: File, to: File)

    /**
     * Writes text into the given output file.
     * @param source the source of the text
     * @param to the location of the output file
     */
    fun save(source: String, to: File)

    /**
     * Merges the given XML source into the given destination file (or just writes it if the
     * destination file does not exist).
     */
    fun mergeXml(source: String, to: File)

    /**
     * Creates a directory at the specified location (if not already present).
     * This will also create any parent directories that don't exist, as well.
     */
    fun createDirectory(at: File)

    /** Records that this file should be opened in app. */
    fun open(file: File)

    /**
     * Adds "plugins { id '`plugin`' }" statement to the module build.gradle[.kts] file and
     * if settings.gradle[.kts] has a `pluginManagement` block, it also adds
     * "plugins { id '`plugin`' version '`revision`'}" statement to the project top level
     * build.gradle[.kts] file.
     **/
    fun applyPlugin(plugin: String, revision: String?, minRev: String? = null)

    /**
     * Adds "plugins { id '`plugin`' }" statement to the [module] build.gradle[.kts] file and
     * if settings.gradle[.kts] has a `pluginManagement` block, it also adds
     * "plugins { id '`plugin`' version '`revision`'}" statement to the project top level
     * build.gradle[.kts] file.
     **/
    fun applyPluginInModule(
        plugin: String,
//        module: Module,
        revision: String?,
        minRev: String? = null,
    )

    /**
     * Records a classpath dependency. If settings.gradle[.kts] has a `pluginManagement` block
     * this call is ignored, unless `forceAdding` is `true`
     **/
    fun addClasspathDependency(
        mavenCoordinate: String, minRev: String? = null, forceAdding: Boolean = false)

    /**
     * Determines if a module/project already have a dependency.
     * @param moduleDir determines a module to check. The current module will be used if it is null.
     */
    fun hasDependency(mavenCoordinate: String, moduleDir: File? = null): Boolean

    /**
     * Records a library dependency
     * Old [configuration]s such as "compile" will be converted to new ones ("implementation") in
     * later stages if Gradle supports it.
     *
     * @param mavenCoordinate coordinate of dependency to be added in Maven format
     * (e.g androidx.appcompat:appcompat:1.1.0).
     * @param configuration Gradle configuration to use.
     * @param minRev If [minRev] is present, [minRev] or a higher number is used as the version of
     * the dependency.
     * @param moduleDir determines a module to add the dependency. The current module will be used
     * if it is null.
     * @param toBase If true the dependency will be added to a base module if it exists.
     *        Example: Libraries with manifest resources added to a Dynamic Feature Module need to
     *        be added to the base module.
     */
    fun addDependency(
        mavenCoordinate: String,
        configuration: String = "compile",
        minRev: String? = null,
        moduleDir: File? = null,
        toBase: Boolean = false
    )

    /**
     * Records a module dependency.
     * Old [configuration]s such as "compile" will be converted to new ones ("implementation") in
     * later stages if Gradle supports it.
     *
     * @param configuration Gradle configuration to use.
     * @param moduleName name of a module on which something depends. Should not start with ':'.
     * @param toModule path to the module which depends on [moduleName].
     */
    fun addModuleDependency(configuration: String, moduleName: String, toModule: File)

    /**
     * Adds a platform artifact dependency, for using Maven BOMs to set the versions of other
     * dependencies.
     */
    fun addPlatformDependency(
        mavenCoordinate: String,
        configuration: String = "implementation",
        enforced: Boolean = false
    )

    /**
     * Adds a new entry to 'sourceSets' block of Gradle build file.
     *
     * @param type type of the source set.
     * @param name source set name that is created/modified.
     * @param dir path to the source set folder (or file if [type] is [SourceSetType.MANIFEST]).
     * */
    fun addSourceSet(type: SourceSetType, name: String, dir: File)

    /** Initializes the variable with [name] to [value] in the ext block of global Gradle build file. */
    fun setExtVar(name: String, value: String)

    /**
     * Returns the variable with [name] in the ext block of global Gradle build file.
     *
     * @return the value of the variable. Returns [valueIfNotFound] if the variable with [name] isn't
     * found in the ext block.
     */
    fun getExtVar(name: String, valueIfNotFound: String): String

    /**
     * Looks for the given classpath dependency coordinate, in the project base, and returns the
     * version variable name on it. For example if the base project has a classpath dependency of
     * 'org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version' this function returns
     * 'kotlin_version'.
     * If the dependency has no variable, or the variable can't be determined, it returns the
     * specified default value.
     *
     * @param mavenCoordinate coordinate of class path dependency to be added in Maven format
     * (e.g androidx.appcompat:appcompat).
     * @param valueIfNotFound value to return if the dependency has no variable, or the variable
     * can't be determined.
     */
    fun getClasspathDependencyVarName(mavenCoordinate: String, valueIfNotFound: String): String

    /**
     * Looks for the given dependency coordinate and returns the version variable name on it.
     * For example if the module has a dependency of 'androidx.appcompat:appcompat:$appcompat_version'
     * this function returns 'appcompat_version'.
     * If the dependency has no variable, or the variable can't be determined, it returns the
     * specified default value.
     *
     * @param mavenCoordinate coordinate of the dependency to be added in Maven format
     * (e.g androidx.appcompat:appcompat).
     * @param valueIfNotFound value to return if the dependency has no variable, or the variable
     * can't be determined.
     */
    fun getDependencyVarName(mavenCoordinate: String, valueIfNotFound: String): String

    /**
     * Adds a module dependency to global settings.gradle[.kts] file.
     */
    fun addIncludeToSettings(moduleName: String)

    /**
     * Adds a new build feature to android block. For example, may enable compose.
     */
    fun setBuildFeature(name: String, value: Boolean)

    /**
     * Sets the view binding support. This exists only for AGP 3.6 because syntax is different from
     * AGP 3.6 and AGP 4.0+. View binding can be configured using [setBuildFeature] with AGP 4.0+.
     */
    fun setViewBinding(value: Boolean)

    /**
     * Sets Compose Options field values
     */
    fun setComposeOptions(kotlinCompilerExtensionVersion: String?)

    /**
     * Sets sourceCompatibility and targetCompatibility in compileOptions and (if needed) jvmTarget
     * in kotlinOptions.
     */
    fun requireJavaVersion(version: String, kotlinSupport: Boolean = false)
}

enum class SourceSetType {
    AIDL,
    ASSETS,
    JAVA,
    MANIFEST,
    RENDERSCRIPT,
    RES,
    RESOURCES
}