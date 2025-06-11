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

/** Execution engine for the instructions in a recipe. */
interface RecipeExecutor {
    /**
     * Copies the given source file into the given destination file (where the
     * source is allowed to be a directory, in which case the whole directory is copied
     * recursively).
     */
    fun copy(from: File, to: File)

    /**
     * Writes text into the given output file.
     *
     * @param source the source of the text
     * @param to the location of the output file
     */
    fun save(source: String, to: File)

    /**
     * Appends text to the given output file. If the existing text does not end with a newline,
     * one is added before the appended text.
     */
    fun append(source: String, to: File)

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

    /** Records that this file should be opened in Androlabs. */
    fun open(file: File)

    /**
     * Adds "plugins { id '`plugin`' }" statement to the module build.gradle[.kts] file and
     * if settings.gradle[.kts] has a `pluginManagement` block, it also adds
     * "plugins { id '`plugin`' version '`revision`'}" statement to the project top level
     * build.gradle[.kts] file.
     **/
    fun applyPlugin(plugin: String, revision: String?, minRev: String? = null)

    /**
     * Add plugins according to the existing project setup. First it adds plugin to catalog
     * if project has it. Then it add plugin project level declaration to first component
     * that exists: settings plugins management block, root project plugins block (if exists),
     * dependencies classpath if it's an old syntax. In case nothing was found - we force
     * adding plugin alias to root project plugins block.
     *
     * Plugin version must be located in classpath parameter.
     */
    fun addPlugin(pluginId: String, classpath: String)

    fun addPlugin(pluginId: String, classpathModule: String, version: String)

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

    fun applyPluginWithClasspathInModule(
        pluginId: String,
//        module: Module,
        classpathModule: String,
        version: String,
    )

    /**
     * Records a classpath dependency. If settings.gradle[.kts] has a `pluginManagement` block
     * this call is ignored, unless `forceAdding` is `true`
     **/
    fun addClasspathDependency(
        mavenCoordinate: String, minRev: String? = null, forceAdding: Boolean = false)

    /**
     * Records a library dependency Old [configuration]s such as "compile" will be converted to
     * new ones ("implementation") in later stages if Gradle supports it.
     *
     * @param mavenCoordinate coordinate of dependency to be added in Maven format (e.g
     *   androidx.appcompat:appcompat:1.1.0).
     * @param configuration Gradle configuration to use.
     * @param minRev If [minRev] is present, [minRev] or a higher number is used as the version
     *   of the dependency.
     * @param moduleDir determines a module to add the dependency. The current module will be
     *   used if it is null.
     * @param toBase If true the dependency will be added to a base module if it exists. Example:
     *   Libraries with manifest resources added to a Dynamic Feature Module need to be added to
     *   the base module.
     */
    fun addDependency(
        mavenCoordinate: String,
        configuration: String = "implementation",
        minRev: String? = null,
        moduleDir: File? = null,
        toBase: Boolean = false,
        sourceSetName: String? = null,
    )

    /**
     * Records a module dependency. Old [configuration]s such as "compile" will be converted to
     * new ones ("implementation") in later stages if Gradle supports it.
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
        enforced: Boolean = false,
    )

    /**
     * Records a file dependency, e.g. implementation(files("./libs/my_lib.aar"))
     *
     * @param configuration Gradle configuration to use.
     * @param file path to the file to depend on
     */
    fun addFileDependency(file: String, configuration: String = "implementation")

    /**
     * Appends text to the project-level gradle.properties file, if the specified property doesn't
     * exist in the file. This is sort of a hack, because GradlePropertiesModel is read-only
     *
     * @param propertyName the property to check if already exists
     * @param textToAdd the raw text to append to the file
     */
    fun addProjectGradleProperty(propertyName: String, textToAdd: String)

    /**
     * Adds a new entry to 'sourceSets' block of Gradle build file.
     *
     * @param type type of the source set.
     * @param name source set name that is created/modified.
     * @param dir path to the source set folder (or file if [type] is [SourceSetType.MANIFEST]).
     * */
    fun addSourceSet(type: SourceSetType, name: String, dir: File)

    /**
     * Initializes the variable with [name] to [value] in the ext block of global Gradle build
     * file.
     */
    fun setExtVar(name: String, value: String)

    /**
     * Returns the variable with [name] in the ext block of global Gradle build file.
     *
     * @return the value of the variable. Returns [valueIfNotFound] if the variable with [name]
     *   isn't found in the ext block.
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
     *   (e.g androidx.appcompat:appcompat).
     * @param valueIfNotFound value to return if the dependency has no variable, or the variable
     *   can't be determined.
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
     *   (e.g androidx.appcompat:appcompat).
     * @param valueIfNotFound value to return if the dependency has no variable, or the variable
     *   can't be determined.
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
     * Sets Compose Options field values
     */
    fun setComposeOptions(kotlinCompilerExtensionVersion: String?)

    /**
     * Sets sourceCompatibility and targetCompatibility in compileOptions and (if needed)
     * jvmTarget in kotlinOptions, choosing the version from a centralized place
     */
    fun setJavaKotlinCompileOptions(isKotlin: Boolean = false)

    /**
     * Adds a dynamic feature [name] to [toModule]'s build.gradle[.kts].
     *
     * @param name name of a dynamic feature which should be added.
     * @param toModule path to base feature module dir
     */
    fun addDynamicFeature(name: String, toModule: File)

    /**
     * Gets the Java version used by the Gradle JVM as a String for build.gradle files, for example
     * JavaVersion.VERSION_17
     *
     * @param defaultVersion the default version to return if the JVM can't be found
     * @return the Java version
     */
    fun getJavaVersion(defaultVersion: String = "JavaVersion.VERSION_17"): String
}

enum class SourceSetType {
    AIDL,
    ASSETS,
    JAVA,
    MANIFEST,
    RES,
    RESOURCES
}