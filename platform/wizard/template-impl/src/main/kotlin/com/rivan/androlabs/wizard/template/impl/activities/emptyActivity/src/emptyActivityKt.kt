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

package com.rivan.androlabs.wizard.template.impl.activities.emptyActivity.src

import com.rivan.androlabs.wizard.template.api.escapeKotlinIdentifier
import com.rivan.androlabs.wizard.template.api.getMaterialComponentName
import com.rivan.androlabs.wizard.template.api.renderIf

fun emptyActivityKt(
    packageName: String,
    namespace: String,
    activityClass: String,
    layoutName: String,
    generateLayout: Boolean,
    useAndroidX: Boolean,
) = """
package ${escapeKotlinIdentifier(packageName)}
 
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import ${getMaterialComponentName("android.support.v7.app.AppCompatActivity", useAndroidX)}
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
${renderIf(namespace != packageName) { "import ${escapeKotlinIdentifier(namespace)}.R" }}

class $activityClass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ${
    renderIf(generateLayout) {
        """enableEdgeToEdge()
        setContentView(R.layout.$layoutName)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }"""
    }
}
    }
}
"""