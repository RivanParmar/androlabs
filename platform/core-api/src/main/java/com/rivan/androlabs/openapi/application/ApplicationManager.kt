// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.application

interface ApplicationManager {

    suspend fun <T> runReadAction(block: suspend () -> T): T

    suspend fun <T> runWriteAction(block: suspend () -> T): T

    val isReadAccessAllowed: Boolean

    val isWriteAccessAllowed: Boolean

    companion object {
        @Volatile
        var instance: ApplicationManager? = null
    }
}