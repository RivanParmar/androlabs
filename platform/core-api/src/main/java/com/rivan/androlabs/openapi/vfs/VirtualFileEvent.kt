// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.vfs

sealed class VirtualFileEvent {

    abstract val file: VirtualFile
    abstract val parent: VirtualFile?
    abstract val requestor: Any?
    abstract val oldModificationStamp: Long
    abstract val newModificationStamp: Long
    abstract val isFromRefresh: Boolean

    data class PropertyChanged(
        override val file: VirtualFile,
        override val parent: VirtualFile?,
        override val requestor: Any?,
        override val oldModificationStamp: Long,
        override val newModificationStamp: Long,
        override val isFromRefresh: Boolean = false,
        val propertyName: String,
        val oldValue: Any?,
        val newValue: Any?,
    ) : VirtualFileEvent()

    data class ContentsChanged(
        override val file: VirtualFile,
        override val parent: VirtualFile?,
        override val requestor: Any?,
        override val oldModificationStamp: Long,
        override val newModificationStamp: Long,
        override val isFromRefresh: Boolean = false,
    ) : VirtualFileEvent()

    data class FileCreated(
        override val file: VirtualFile,
        override val parent: VirtualFile?,
        override val requestor: Any?,
        override val oldModificationStamp: Long,
        override val newModificationStamp: Long,
        override val isFromRefresh: Boolean = false,
    ) : VirtualFileEvent()

    data class FileDeleted(
        override val file: VirtualFile,
        override val parent: VirtualFile?,
        override val requestor: Any?,
        override val oldModificationStamp: Long,
        override val newModificationStamp: Long,
        override val isFromRefresh: Boolean = false,
    ) : VirtualFileEvent()

    data class FileMoved(
        override val file: VirtualFile,
        override val parent: VirtualFile?,
        override val requestor: Any?,
        override val oldModificationStamp: Long,
        override val newModificationStamp: Long,
        override val isFromRefresh: Boolean = false,
        val oldParent: VirtualFile,
        val newParent: VirtualFile,
    ) : VirtualFileEvent()

    data class FileCopied(
        override val file: VirtualFile,
        override val parent: VirtualFile?,
        override val requestor: Any?,
        override val oldModificationStamp: Long,
        override val newModificationStamp: Long,
        override val isFromRefresh: Boolean = false,
        val originalFile: VirtualFile,
    ) : VirtualFileEvent()
}