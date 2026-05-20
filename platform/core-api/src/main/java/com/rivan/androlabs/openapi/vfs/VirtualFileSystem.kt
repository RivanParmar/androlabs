// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.vfs

interface VirtualFileSystem {

    val protocol: String

    val isReadOnly: Boolean

    fun findFileByPath(path: String): VirtualFile?

    suspend fun refresh(projectRoots: List<String>)

    suspend fun refreshPaths(paths: List<String>)

    fun deleteFile(requestor: Any?, file: VirtualFile)

    fun moveFile(requestor: Any?, file: VirtualFile, newParent: VirtualFile)

    fun renameFile(requestor: Any?, file: VirtualFile, newName: String)

    fun copyFile(
        requestor: Any?,
        virtualFile: VirtualFile,
        newParent: VirtualFile,
        copyName: String,
    ): VirtualFile

    fun createChildFile(requestor: Any?, directory: VirtualFile, fileName: String): VirtualFile

    fun createChildDirectory(requestor: Any?, directory: VirtualFile, dirName: String): VirtualFile

    fun isValidName(name: String): Boolean {
        return !name.isEmpty() && name.indexOf('\\') < 0 && name.indexOf('/') < 0
    }
}