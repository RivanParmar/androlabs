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

package com.rivan.androlabs.wizard.template.impl.activities.common.res.values

import com.rivan.androlabs.wizard.template.api.activityToLayout
import com.rivan.androlabs.wizard.template.api.renderIf

fun manifestStrings(
    activityClass: String,
    isNewModule: Boolean,
    generateActivityTitle: Boolean,
): String {
    val innerBlock = renderIf(!isNewModule && generateActivityTitle) {
        """<string name="title_${activityToLayout(activityClass)}">$activityClass</string>"""
    }

    return """
<resources>
    $innerBlock
</resources>
"""
}