package com.rivan.androlabs.openapi.project

import org.jetbrains.annotations.ApiStatus
import java.io.File
import java.nio.file.Path


interface ProjectStore {

    fun getProjectBasePath(): Path

    @ApiStatus.Internal
    fun getLocationHash(): String

    fun getProjectName(): String

    fun getStorageScheme(): StorageScheme

    fun getPresentableUrl(): String

    /**
     * The path to project configuration file - `misc.xml` for directory-based and `*.ipr` for file-based.
     */
    fun getProjectFilePath(): Path

    fun getWorkspacePath(): Path

    fun isProjectFile(file: File): Boolean

    /**
     * The directory of project configuration files for directory-based project or null for file-based.
     */
    fun getDirectoryStorePath(): Path?

    fun setPath(path: Path)

//    fun getProjectWorkspaceId(): String?
}