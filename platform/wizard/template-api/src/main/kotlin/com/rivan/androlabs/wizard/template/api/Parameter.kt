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

import kotlin.reflect.KClass

/**
 * This is a parameter which is a part of [Template].
 *
 * Each parameter will be rendered to its own field when rendering UI from [Template], albeit
 * possibly disabled or hidden.
 * A user should provide [value]s to all parameters via interacting with UI.
 * Later this data is passed to the [Recipe] and used to render actual template files.
 */
sealed class Parameter<T> {
    /** Name of the parameter. Should be unique. */
    abstract val name: String
    open val help: String? = null
    abstract val defaultValue: T
    abstract val value: T
    // should be updated only by [Parameters]
    internal lateinit var wizardParameterData: WizardParameterData

    /**
     * Tells if the [Parameter] should be shown in UI.
     *
     * We do not show parameters which are not visible in UI, but use them (fill with data and send
     * to the [Recipe]).
     * @see enabled
     */
    val isVisibleAndEnabled: Boolean
        get() = enabled && visible

    /**
     * Returns false if the [Parameter] should be completely ignored (sometimes will be rendered in
     * gray in UI).
     */
    abstract val enabled: Boolean
    abstract val visible: Boolean
}

/**
 * A wrapper which overrides some [Parameter] methods with given lambdas.
 *
 * Actual parameters should inherit this, not [Parameter].
 */
sealed class DslParameter<T>(
    private val _visible: WizardParameterData.() -> Boolean = { true },
    private val _enabled: WizardParameterData.() -> Boolean = { true }
): Parameter<T>() {
    override val enabled: Boolean get() = wizardParameterData._enabled()
    override val visible: Boolean get() = wizardParameterData._visible()
}

/**
 * String parameter. Rendered as a text field in UI.
 */
data class StringParameter(
    override val name: String,
    override val help: String? = null,
    private val _visible: WizardParameterData.() -> Boolean = { true },
    private val _enabled: WizardParameterData.() -> Boolean = { true },
    override val defaultValue: String,
    private val _suggest: WizardParameterData.() -> String? = { null }
) : DslParameter<String>(_visible, _enabled) {
    override val value: String = defaultValue

    /**
     * Value suggested by the Studio. If it was evaluated to null, then [defaultValue] is used.
     * Often calculated using different parameters, e.g "activity_super" layout name generated from
     * "SuperActivity".
     */
    fun suggest() = wizardParameterData._suggest()
}

/**
 * Enum parameter. Rendered as a combo box in UI.
 */
data class EnumParameter<T: Enum<T>>(
    private val enumClass: KClass<T>,
    override val name: String,
    override val help: String? = null,
    private val _visible: WizardParameterData.() -> Boolean = { true },
    private val _enabled: WizardParameterData.() -> Boolean = { true },
    override val defaultValue: T
) : DslParameter<T>(_visible, _enabled) {
    override var value: T = defaultValue
    val options: Array<T> = enumClass.java.enumConstants
    fun fromString(string: String): T? = options.find { it.name == string }
}

/**
 * Boolean parameter. Rendered as a checkbox in UI.
 */
data class BooleanParameter(
    override val name: String,
    override val help: String? = null,
    private val _visible: WizardParameterData.() -> Boolean = { true },
    private val _enabled: WizardParameterData.() -> Boolean = { true },
    override val defaultValue: Boolean
) : DslParameter<Boolean>(_visible, _enabled) {
    override var value: Boolean = defaultValue
}