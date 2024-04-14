// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modified by Rivan Parmar on 15/04/2024

package com.rivan.androlabs.ide.impl

import com.rivan.androlabs.openapi.project.Project
import com.rivan.androlabs.openapi.project.ProjectOpenedCallback
import org.jetbrains.annotations.ApiStatus.Internal
import java.util.function.Predicate

data class OpenProjectTask internal constructor(
    val forceOpenInNewActivity: Boolean,
    val projectToClose: Project?,
    val isNewProject: Boolean = false,
    val project: Project?,
    val projectName: String?,
    val showWelcomeScreen: Boolean,
    val callback: ProjectOpenedCallback?,
    val line: Int,
    val column: Int,
    val runConfigurators: Boolean,
    val runConversionBeforeOpen: Boolean,
    val isProjectCreatedWithWizard: Boolean,
    val beforeInit: ((Project) -> Unit)?,
    val beforeOpen: (suspend (Project) -> Boolean)?,

    val preventIprLookup: Boolean,
    val processorChooser: ((List<Any>) -> Any)?,
    val implOptions: Any?,
) {
    @Internal
    constructor(
        forceOpenInNewActivity: Boolean = false,
        projectToClose: Project? = null,
        isNewProject: Boolean = false,
    ) : this(
        forceOpenInNewActivity = forceOpenInNewActivity,
        projectToClose = projectToClose,
        isNewProject = isNewProject,

        project = null,
        projectName = null,

        showWelcomeScreen = true,
        callback = null,
        line = -1,
        column = -1,

        runConfigurators = false,
        runConversionBeforeOpen = true,
        isProjectCreatedWithWizard = false,

        beforeInit = null,

        beforeOpen = null,
        preventIprLookup = false,
        processorChooser = null,

        implOptions = null,
    )

    companion object {
        @JvmStatic
        fun build(): OpenProjectTask = OpenProjectTask()
    }

    fun withForceOpenInNewActivity(forceOpenInNewActivity: Boolean): OpenProjectTask =
        copy(forceOpenInNewActivity = forceOpenInNewActivity)
    fun withProjectToClose(projectToClose: Project?): OpenProjectTask =
        copy(projectToClose = projectToClose)
    fun asNewProject(): OpenProjectTask = copy(isNewProject = true)
    fun withProject(project: Project?): OpenProjectTask = copy(project = project)
    fun withProjectName(projectName: String?): OpenProjectTask = copy(projectName = projectName)
}

class OpenProjectTaskBuilder internal constructor() {
    var projectName: String? = null

    var forceOpenInNewActivity: Boolean = false

    var isNewProject: Boolean = false

    var runConfigurators: Boolean = false

    var isProjectCreatedWithWizard: Boolean = false
    var runConversionBeforeOpen: Boolean = true
    var preventIprLookup: Boolean = false

    var projectToClose: Project? = null

    @Internal
    var beforeOpen: (suspend (Project) -> Boolean)? = null

    @Internal
    var beforeInit: ((Project) -> Unit)? = null
    var callback: ProjectOpenedCallback? = null

    var showWelcomeScreen: Boolean = true

    var implOptions: Any? = null

    var line: Int = -1
    var column: Int = -1

    fun withBeforeOpenCallback(callback: Predicate<Project>) {
        beforeOpen = { callback.test(it) }
    }

    @Internal
    var processorChooser: ((List<Any>) -> Any)? = null

    var project: Project? = null

    internal inline fun build(builder: OpenProjectTaskBuilder.() -> Unit): OpenProjectTask {
        builder()
        return OpenProjectTask(
            forceOpenInNewActivity = forceOpenInNewActivity,

            projectToClose = projectToClose,

            projectName = projectName,
            isNewProject = isNewProject,
            runConfigurators = runConfigurators,
            isProjectCreatedWithWizard = isProjectCreatedWithWizard,
            runConversionBeforeOpen = runConversionBeforeOpen,
            showWelcomeScreen = showWelcomeScreen,

            beforeOpen = beforeOpen,
            beforeInit = beforeInit,
            callback = callback,

            preventIprLookup = preventIprLookup,
            processorChooser = processorChooser,

            implOptions = implOptions,

            line = line,
            column = column,

            project = project,
        )
    }
}

@Internal
fun OpenProjectTask(buildAction: OpenProjectTaskBuilder.() -> Unit): OpenProjectTask {
    return OpenProjectTaskBuilder().build(buildAction)
}
