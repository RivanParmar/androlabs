package com.rivan.androlabs.openapi.module

import com.rivan.androlabs.openapi.project.Project
import org.jetbrains.annotations.ApiStatus
import java.io.File
import java.nio.file.Path

interface Module {

    @ApiStatus.Internal
    fun getModuleFile(): File?

    @ApiStatus.Internal
    fun getModuleFilePath(): String {
        return getModuleNioFile().toString().replace(File.separatorChar, '/')
    }

    @ApiStatus.Internal
    fun getModuleNioFile(): Path

    fun getProject(): Project

    fun getName(): String

    fun isLoaded(): Boolean
}