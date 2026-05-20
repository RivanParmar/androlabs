// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

// Modifications Copyright (C) 2026 Rivan Parmar

package com.rivan.androlabs.openapi.util

import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.fetchAndIncrement

interface ModificationTracker {
    val modificationCount: Long

    companion object {
        val NEVER_CHANGED = object : ModificationTracker {
            override val modificationCount: Long
                get() = 0L
        }

        @OptIn(ExperimentalAtomicApi::class)
        val EVER_CHANGED = object : ModificationTracker {
            private val counter = AtomicLong(0L)

            override val modificationCount: Long
                get() = counter.fetchAndIncrement()
        }
    }
}