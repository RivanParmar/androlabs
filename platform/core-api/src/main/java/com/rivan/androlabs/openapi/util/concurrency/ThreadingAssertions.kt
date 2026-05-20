// Copyright 2000-2025 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.util.concurrency

import com.rivan.androlabs.openapi.application.ApplicationManager

object ThreadingAssertions {

    const val MUST_EXECUTE_IN_READ_ACTION =
        "Read access is allowed from inside read-action only (see ApplicationManager.runReadAction())"

    const val MUST_EXECUTE_IN_WRITE_ACTION =
        "Write access is allowed from inside write-action only (see ApplicationManager.runWriteAction())"

    fun assertReadAccess() {
        val applicationManager = ApplicationManager.instance ?: return
        if (!applicationManager.isReadAccessAllowed) {
            throw IllegalStateException(MUST_EXECUTE_IN_READ_ACTION)
        }
    }

    fun assertWriteAccess() {
        val applicationManager = ApplicationManager.instance ?: return
        if (!applicationManager.isWriteAccessAllowed) {
            throw IllegalStateException(MUST_EXECUTE_IN_WRITE_ACTION)
        }
    }
}