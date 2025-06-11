/*
 * Copyright (C) 2021 The Android Open Source Project
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

package com.rivan.androlabs.wizard.template.impl.activities.basicActivity

import com.rivan.androlabs.wizard.template.api.BooleanParameter
import com.rivan.androlabs.wizard.template.api.Category
import com.rivan.androlabs.wizard.template.api.CheckBoxWidget
import com.rivan.androlabs.wizard.template.api.Constraint.CLASS
import com.rivan.androlabs.wizard.template.api.Constraint.LAYOUT
import com.rivan.androlabs.wizard.template.api.Constraint.NAVIGATION
import com.rivan.androlabs.wizard.template.api.Constraint.NONEMPTY
import com.rivan.androlabs.wizard.template.api.Constraint.UNIQUE
import com.rivan.androlabs.wizard.template.api.Divider
import com.rivan.androlabs.wizard.template.api.FormFactor
import com.rivan.androlabs.wizard.template.api.LanguageWidget
import com.rivan.androlabs.wizard.template.api.ModuleTemplateData
import com.rivan.androlabs.wizard.template.api.PackageNameWidget
import com.rivan.androlabs.wizard.template.api.StringParameter
import com.rivan.androlabs.wizard.template.api.TemplateConstraint
import com.rivan.androlabs.wizard.template.api.TemplateData
import com.rivan.androlabs.wizard.template.api.TextFieldWidget
import com.rivan.androlabs.wizard.template.api.WizardUiContext
import com.rivan.androlabs.wizard.template.api.activityToLayout
import com.rivan.androlabs.wizard.template.api.booleanParameter
import com.rivan.androlabs.wizard.template.api.classToResource
import com.rivan.androlabs.wizard.template.api.layoutToActivity
import com.rivan.androlabs.wizard.template.api.stringParameter
import com.rivan.androlabs.wizard.template.api.template
import com.rivan.androlabs.wizard.template.impl.activities.common.MIN_API
import com.rivan.androlabs.wizard.template.impl.defaultPackageNameParameter
import java.io.File

val basicActivityTemplate get() = template {
    name = "Basic Views Activity"
    minApi = MIN_API
    description = "Creates a new basic activity"

    category = Category.Activity
    formFactor = FormFactor.Mobile
    screens = listOf(
        WizardUiContext.ActivityGallery,
        WizardUiContext.MenuEntry,
        WizardUiContext.NewProject,
        WizardUiContext.NewModule,
    )
    constraints = listOf(TemplateConstraint.AndroidX, TemplateConstraint.Material3)

    lateinit var activityClass: StringParameter
    val layoutName: StringParameter = stringParameter {
        name = "Layout Name"
        constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
        suggest = { activityToLayout(activityClass.value) }
        default = "activity_main"
        help = "The name of the layout to create for the activity"
        loggable = true
    }

    activityClass = stringParameter {
        name = "Activity Name"
        constraints = listOf(CLASS, UNIQUE, NONEMPTY)
        suggest = { layoutToActivity(layoutName.value) }
        default = "MainActivity"
        help = "The name of the activity class to create"
        loggable = true
    }

    val menuName: StringParameter = stringParameter {
        name = "Menu Resource File"
        constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
        suggest = { "menu_${classToResource(activityClass.value)}" }
        visible = { isNewModule }
        default = "menu_main"
        help = "The name of the resource file to create for the items"
        loggable = true
    }

    val isLauncher: BooleanParameter = booleanParameter {
        name = "Launcher Activity"
        visible = { !isNewModule }
        default = false
        help = "If true, this activity will have a CATEGORY_LAUNCHER intent filter, making it visible in the launcher"
    }

    val contentLayoutName: StringParameter = stringParameter {
        name = "Content Layout Name"
        constraints = listOf(LAYOUT, UNIQUE)
        suggest = { activityToLayout(activityClass.value, "content") }
        default = "content_main"
        visible = { false }
        help = "The name of the App Bar layout to create for the activity"
        loggable = true
    }

    val firstFragmentLayoutName: StringParameter = stringParameter {
        name = "First fragment Layout Name"
        constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
        default = "fragment_first"
        visible = { false }
        help = "The name of the layout of the Fragment as the initial destination in Navigation"
        loggable = true
    }

    val secondFragmentLayoutName: StringParameter = stringParameter {
        name = "Second fragment Layout Name"
        constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
        default = "fragment_second"
        visible = { false }
        help = "The name of the layout of the Fragment as the second destination in Navigation"
        loggable = true
    }

    val navGraphName = stringParameter {
        name = "Navigation graph name"
        default = "nav_graph"
        help = "The name of the navigation graph"
        visible = { false }
        constraints = listOf(NAVIGATION, UNIQUE)
        suggest = { "nav_graph" }
        loggable = true
    }

    val packageName = defaultPackageNameParameter

    widgets(
        TextFieldWidget(activityClass),
        TextFieldWidget(layoutName),
        TextFieldWidget(menuName),
        CheckBoxWidget(isLauncher),
        Divider, // for example
        PackageNameWidget(packageName),
        LanguageWidget(),

        // Invisible widgets. Defining these to impose constraints
        TextFieldWidget(contentLayoutName),
        TextFieldWidget(firstFragmentLayoutName),
        TextFieldWidget(secondFragmentLayoutName),
        TextFieldWidget(navGraphName),
    )

    thumb {
        File("basic-activity-material3").resolve("template_basic_activity_material3.png")
    }

    recipe = { data: TemplateData ->
        data as ModuleTemplateData

        generateBasicActivity(
            moduleData = data,
            activityClass = activityClass.value,
            layoutName = layoutName.value,
            contentLayoutName = contentLayoutName.value,
            packageName = packageName.value,
            menuName = menuName.value,
            isLauncher = isLauncher.value,
            firstFragmentLayoutName = firstFragmentLayoutName.value,
            secondFragmentLayoutName = secondFragmentLayoutName.value,
            navGraphName = navGraphName.value
        )
    }
}