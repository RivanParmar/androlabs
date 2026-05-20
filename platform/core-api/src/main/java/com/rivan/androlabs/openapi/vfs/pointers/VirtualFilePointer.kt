// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.vfs.pointers

import com.rivan.androlabs.openapi.vfs.VirtualFile

interface VirtualFilePointer {

    val fileName: String

    val file: VirtualFile?

    val url: String

    val isValid: Boolean
}