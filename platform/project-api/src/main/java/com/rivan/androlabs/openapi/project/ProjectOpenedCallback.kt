package com.rivan.androlabs.openapi.project

import com.rivan.androlabs.openapi.module.Module

/**
 * Called when a new project is opened or attached as a module to the currently opened project.
 */
interface ProjectOpenedCallback {
    fun projectOpened(project: Project, module: Module)
}