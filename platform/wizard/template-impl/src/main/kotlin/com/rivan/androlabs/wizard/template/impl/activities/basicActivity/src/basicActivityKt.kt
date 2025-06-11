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

package com.rivan.androlabs.wizard.template.impl.activities.basicActivity.src

import com.rivan.androlabs.wizard.template.api.Language
import com.rivan.androlabs.wizard.template.api.escapeKotlinIdentifier
import com.rivan.androlabs.wizard.template.api.getMaterialComponentName
import com.rivan.androlabs.wizard.template.api.renderIf
import com.rivan.androlabs.wizard.template.impl.activities.common.findViewById
import com.rivan.androlabs.wizard.template.impl.activities.common.importViewBindingClass
import com.rivan.androlabs.wizard.template.impl.activities.common.layoutToViewBindingClass

fun basicActivityKt(
    isNewProject: Boolean,
    applicationPackage: String?,
    packageName: String,
    useAndroidX: Boolean,
    activityClass: String,
    layoutName: String,
    menuName: String,
    navHostFragmentId: String,
    isViewBindingSupported: Boolean,
): String {
    val applicationPackageBlock = renderIf(applicationPackage != null) { "import $applicationPackage.R" }
    val newProjectImportBlock = renderIf(isNewProject) {
        """
import android.view.Menu
import android.view.MenuItem
"""
    }

    val newProjectBlock2 = renderIf(isNewProject) {
        """
        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.$menuName, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            return when(item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
        }
        """
    }

    val contentViewBlock = if (isViewBindingSupported) """
       binding = ${layoutToViewBindingClass(layoutName)}.inflate(layoutInflater)
       setContentView(binding.root)
    """ else "setContentView(R.layout.$layoutName)"

    return """
package ${escapeKotlinIdentifier(packageName)}

import android.os.Bundle
${renderIf(!isViewBindingSupported) {"""
import ${getMaterialComponentName("android.support.design.widget.FloatingActionButton", useAndroidX)}
"""}}
import ${getMaterialComponentName("android.support.design.widget.Snackbar", useAndroidX)}
import ${getMaterialComponentName("android.support.v7.app.AppCompatActivity", useAndroidX)}
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
$newProjectImportBlock
$applicationPackageBlock
${importViewBindingClass(isViewBindingSupported, packageName, applicationPackage, layoutName, Language.Kotlin)}

class $activityClass : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
${renderIf(isViewBindingSupported) {"""
    private lateinit var binding: ${layoutToViewBindingClass(layoutName)}
"""}}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        $contentViewBlock
        setSupportActionBar(${findViewById(Language.Kotlin, isViewBindingSupported, id = "toolbar")})

        val navController = findNavController(R.id.${navHostFragmentId})
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        ${findViewById(
        Language.Kotlin,
        isViewBindingSupported = isViewBindingSupported,
        id = "fab",
        className = "FloatingActionButton",
    )
    }.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show()
        }
    }
$newProjectBlock2

    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.${navHostFragmentId})
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }
}
"""
}