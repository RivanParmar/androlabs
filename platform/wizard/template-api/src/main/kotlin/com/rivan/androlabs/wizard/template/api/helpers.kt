/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.wizard.template.api

/**
 * This is a special token which means "remove this line".
 *
 * Usually it is used in cases like this:
 * ```
 * val predicate = false
 * val block = renderIf(predicate) { "optional line" }
 * // block == SKIP_LINE
 * val text = """
 * required line 1
 * $block
 * required line 2
 * """
 * ```
 *
 * If we will render a template containing `text` it will contain only two lines without an empty
 * line between them.
 */
const val SKIP_LINE = "[THIS LINE SHOULD NOT BE RENDERED!]"

/**
 * Returns [str] result if [predicate] is true.
 *
 * @see SKIP_LINE
 */
inline fun renderIf(
    predicate: Boolean,
    trim: Boolean = true,
    skipLine: Boolean = true,
    str: () -> String
) = if (predicate)
        if (trim) str().trim() else str()
    else
        SKIP_LINE.takeIf { skipLine }.orEmpty()

/**
 * Returns a new String with [SKIP_LINE] removed.
 *
 * @see SKIP_LINE
 */
fun CharSequence.withoutSkipLines() = this.split("\n")
    .filter { it.trim() != SKIP_LINE }
    .joinToString("\n")
    .replace(SKIP_LINE, "") // for some SKIP_LINEs which are not on their own line