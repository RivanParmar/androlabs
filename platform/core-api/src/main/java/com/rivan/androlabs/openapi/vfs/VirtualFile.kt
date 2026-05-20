// Copyright 2000-2026 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.vfs

import com.rivan.androlabs.openapi.util.ModificationTracker
import com.rivan.androlabs.openapi.util.concurrency.ThreadingAssertions

abstract class VirtualFile protected constructor() : ModificationTracker {

    abstract val name: String

    abstract val fileSystem: VirtualFileSystem

    abstract val path: String

    open val url: String
        get() = VirtualFileManager.constructUrl(fileSystem.protocol, path)

    abstract var isWritable: Boolean

    abstract val isDirectory: Boolean

    open var isValid: Boolean = exists
        get() {
            ThreadingAssertions.assertReadAccess()
            return exists
        }
        internal set

    abstract val parent: VirtualFile?

    abstract val children: List<VirtualFile>

    abstract val modificationStamp: Long

    abstract val timeStamp: Long

    abstract val length: Long

    override val modificationCount: Long
        get() = if (isValid) timeStamp else -1

    open val exists: Boolean
        get() = isValid

    fun findChild(name: String): VirtualFile? {
        return children.find { it.name == name }
    }

    abstract fun readBytes(): ByteArray
    abstract fun writeBytes(content: ByteArray)

    fun rename(requestor: Any?, newName: String) {
        ThreadingAssertions.assertWriteAccess()
        if (name == newName) return
        if (!fileSystem.isValidName(newName)) {
            // TODO: Throw an appropriate exception
        }

        fileSystem.renameFile(requestor, this, newName)
    }

    fun delete(requestor: Any?) {
        ThreadingAssertions.assertWriteAccess()
        fileSystem.deleteFile(requestor, this)
    }

    open fun move(requestor: Any?, newParent: VirtualFile) {
        ThreadingAssertions.assertWriteAccess()
        if (fileSystem != newParent.fileSystem) {
            // TODO: Throw an appropriate exception
        }

        fileSystem.moveFile(requestor, this, newParent)
    }

    fun copy(requestor: Any?, newParent: VirtualFile, copyName: String): VirtualFile {
        if (fileSystem != newParent.fileSystem) {
            // TODO: Throw an appropriate exception
        }

        if (!newParent.isDirectory) {
            // TODO: Throw an appropriate exception
        }

        return fileSystem.copyFile(requestor, this, newParent, copyName)
    }

    fun createChildDirectory(requestor: Any?, name: String): VirtualFile {
        if (!isDirectory) {
            // TODO: Throw an appropriate exception
        }

        if (!isValid) {
            // TODO: Throw an appropriate exception
        }

        if (!fileSystem.isValidName(name)) {
            // TODO: Throw an appropriate exception
        }

        if (findChild(name) != null) {
            // TODO: Throw an appropriate exception
        }

        return fileSystem.createChildDirectory(requestor, this, name)
    }

    fun createChildData(requestor: Any?, name: String): VirtualFile {
        if (!isDirectory) {
            // TODO: Throw an appropriate exception
        }

        if (!isValid) {
            // TODO: Throw an appropriate exception
        }

        if (!fileSystem.isValidName(name)) {
            // TODO: Throw an appropriate exception
        }

        if (findChild(name) != null) {
            // TODO: Throw an appropriate exception
        }

        return fileSystem.createChildFile(requestor, this, name)
    }

    abstract suspend fun refresh(recursive: Boolean)

    open fun hasProperty(property: VFileProperty): Boolean = false

    companion object {
        const val PROP_NAME = "name"
        const val PROP_ENCODING = "encoding"
        const val PROP_WRITABLE = "writable"
        const val PROP_HIDDEN = "hidden"
    }
}