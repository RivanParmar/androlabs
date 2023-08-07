package com.rivan.androlabs.openapi.project

interface Project {

    companion object {
        const val DIRECTORY_STORE_FOLDER = ".idea"
    }

    fun getName(): String

    fun getBasePath(): String?

    fun getProjectFilePath(): String?

    fun getPresentableUrl(): String? = null

    fun save()

    fun scheduleSave() = save()

    // TODO: Check whether the project uses AndroidX libraries
    fun isAndroidX(): Boolean

    fun isOpen(): Boolean

    fun isInitialized(): Boolean
}