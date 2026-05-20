// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.vfs.pointers

import com.rivan.androlabs.openapi.util.SimpleModificationTracker
import com.rivan.androlabs.openapi.vfs.VirtualFile

abstract class VirtualFilePointerManager : SimpleModificationTracker() {

    abstract fun create(url: String): VirtualFilePointer

    abstract fun create(file: VirtualFile): VirtualFilePointer

    abstract fun dispose(pointer: VirtualFilePointer)
}