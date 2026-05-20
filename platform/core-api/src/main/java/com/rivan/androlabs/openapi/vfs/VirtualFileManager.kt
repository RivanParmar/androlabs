// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.vfs

import com.rivan.androlabs.openapi.util.ModificationTracker
import kotlinx.coroutines.flow.SharedFlow
import java.nio.file.Path

abstract class VirtualFileManager : ModificationTracker {

    abstract val fileEvents: SharedFlow<List<VirtualFileEvent>>

    abstract fun getFileSystem(protocol: String?): VirtualFileSystem?

    open fun findFileByUrl(url: String): VirtualFile? = null

    open fun findFileByNioPath(path: Path): VirtualFile? = null

    abstract fun notifyPropertyChanged(
        virtualFile: VirtualFile,
        property: String,
        oldValue: Any,
        newValue: Any,
    )

    abstract suspend fun syncDirtyPaths(urls: List<String>)

    companion object {
        fun constructUrl(protocol: String, path: String): String {
            return "$protocol://$path"
        }
    }
}