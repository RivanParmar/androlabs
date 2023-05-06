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
 * Represents an UI element.
 *
 * Each template has a collection of widgets which is rendered when user selects a template.
 */
interface Widget<T>

/**
 * Represents an UI element which has underlying [parameter].
 *
 * Usually parameters and widgets have 1-1 relationship but there are exceptions:
 *  * Some [Parameter]s may have no widget because they pass data implicitly and don't need user input.
 *  * Some [Widget]s may have no underlying [Parameter] (e.g. [Separator])
 */
sealed class ParameterWidget<T>(val parameter: Parameter<T>): Widget<T>

/**
 * An ordinary text field.
 */
data class TextFieldWidget(val p: StringParameter): ParameterWidget<String>(p)

/**
 * Drop down menu for selecting a language (Kotlin or Java).
 *
 * Exists alongside with [EnumWidget] because it may require special treatment.
 */
class LanguageWidget: Widget<String>

/**
 * [Widget] for selecting package.
 *
 * Looks like combination of [TextFieldWidget] and [EnumWidget] (use can both write and choose from
 * drop down menu).
 */
data class PackageNameWidget(val p: StringParameter): ParameterWidget<String>(p)

/**
 * Drop down menu for any kind of [Enum]. Will include all possible enum values.
 */
data class EnumWidget<T : Enum<T>>(val p: EnumParameter<T>): ParameterWidget<T>(p)

/**
 * An ordinary checkbox.
 */
data class CheckBoxWidget(val p: BooleanParameter): ParameterWidget<Boolean>(p)

/**
 * A Text label with a link to and external website.
 */
data class UrlLinkWidget(val urlName: String, val urlAddress: String): Widget<String>

/**
 * Horizontal separator. Has no functionality.
 */
object Separator : Widget<Nothing>