package com.rivan.builder.project.api

import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderEx
import java.io.File
import java.io.IOException
import java.util.*

interface Module : UserDataHolderEx {

    fun getRootFile(): File

    fun getName(): String {
        return getRootFile().name
    }

    /**
     * Start parsing the project contents such as manifest data, project settings, etc.
     *
     * Implementations may throw an IOException if something went wrong during parsing
     */
    @Throws(IOException::class)
    fun open()

    /**
     * Remove all the indexed files
     */
    fun clear()

    /**
     * Index all the files
     */
    fun index()

    fun addChildModule(module: Module) {

    }

    /**
     * Get the dependencies of the module
     */
    fun getModuleDependencies(): Set<String> {
        return Collections.emptySet()
    }
}
