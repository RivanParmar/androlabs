package com.rivan.androlabs.openapi.project

// TODO: Move this and other project-related classes to a separate module maybe
interface Project {

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

    fun isDefault(): Boolean = false
}