package com.rivan.androlabs.openapi.project

import java.io.File


interface Project {

    companion object {
        const val DIRECTORY_STORE_FOLDER = ".idea"
        const val NAME_FILE = ".name"
    }

    val name: String

    val basePath: String?

    val projectFile: File?

    val projectFilePath: String?

    val workspaceFile: File?

    fun save()

    val isOpen: Boolean

    val isInitialized: Boolean

    fun setProjectName(value: String)

    val isAndroidX: Boolean
}