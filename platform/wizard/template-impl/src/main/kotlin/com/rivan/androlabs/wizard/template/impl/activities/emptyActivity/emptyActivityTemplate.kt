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

// Modifications Copyright (C) 2023 Rivan Parmar

package com.rivan.androlabs.wizard.template.impl.activities.emptyActivity

import com.rivan.androlabs.wizard.template.api.BooleanParameter
import com.rivan.androlabs.wizard.template.api.Category
import com.rivan.androlabs.wizard.template.api.CheckBoxWidget
import com.rivan.androlabs.wizard.template.api.Constraint.CLASS
import com.rivan.androlabs.wizard.template.api.Constraint.LAYOUT
import com.rivan.androlabs.wizard.template.api.Constraint.NONEMPTY
import com.rivan.androlabs.wizard.template.api.Constraint.UNIQUE
import com.rivan.androlabs.wizard.template.api.FormFactor
import com.rivan.androlabs.wizard.template.api.LanguageWidget
import com.rivan.androlabs.wizard.template.api.ModuleTemplateData
import com.rivan.androlabs.wizard.template.api.PackageNameWidget
import com.rivan.androlabs.wizard.template.api.StringParameter
import com.rivan.androlabs.wizard.template.api.TemplateConstraint
import com.rivan.androlabs.wizard.template.api.TextFieldWidget
import com.rivan.androlabs.wizard.template.api.WizardUiContext
import com.rivan.androlabs.wizard.template.api.activityToLayout
import com.rivan.androlabs.wizard.template.api.booleanParameter
import com.rivan.androlabs.wizard.template.api.layoutToActivity
import com.rivan.androlabs.wizard.template.api.stringParameter
import com.rivan.androlabs.wizard.template.api.template
import com.rivan.androlabs.wizard.template.impl.activities.common.MIN_API
import com.rivan.androlabs.wizard.template.impl.defaultPackageNameParameter
import java.io.File

val emptyActivityTemplate get() = template {
    name = "Empty Views Activity"
    minApi = MIN_API
    description = "Creates a new empty activity"

    category = Category.Activity
    formFactor = FormFactor.Mobile
    screens = listOf(
        WizardUiContext.ActivityGallery,
        WizardUiContext.MenuEntry,
        WizardUiContext.NewProject,
        WizardUiContext.NewModule,
    )
    constraints = listOf(TemplateConstraint.AndroidX, TemplateConstraint.Material3)

    val generateLayout: BooleanParameter = booleanParameter {
        name = "Generate a Layout File"
        default = true
        help = "If true, a layout file will be generated"
    }

    lateinit var layoutName: StringParameter

    val activityClass: StringParameter = stringParameter {
        name = "Activity Name"
        constraints = listOf(CLASS, UNIQUE, NONEMPTY)
        suggest = {
            layoutToActivity(layoutName.value)
        }
        default = "MainActivity"
        help = "The name of the activity class to create"
        loggable = true
    }

    layoutName = stringParameter {
        name = "Layout Name"
        constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
        suggest = {
            activityToLayout(activityClass.value)
        }
        default = "activity_main"
        visible = { generateLayout.value }
        help = "The name of the UI layout to create for the activity"
        loggable = true
    }

    val isLauncher: BooleanParameter = booleanParameter {
        name = "Launcher Activity"
        visible = { !isNewModule }
        default = false
        help = "If true, this activity will have a CATEGORY_LAUNCHER intent filter, making it visible in the launcher"
    }

    val packageName = defaultPackageNameParameter

    widgets(
        TextFieldWidget(activityClass),
        CheckBoxWidget(generateLayout),
        TextFieldWidget(layoutName),
        CheckBoxWidget(isLauncher),
        PackageNameWidget(packageName),
        LanguageWidget(),
    )

    thumb {
        File("empty-activity").resolve("template_empty_activity.png")
    }

    recipe = { data ->
        generateEmptyActivity(
            data as ModuleTemplateData, activityClass.value, generateLayout.value,
            layoutName.value, isLauncher.value, packageName.value,
        )
    }
}