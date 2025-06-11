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

interface ParameterBuilder<T> {
    var name: String?
    var help: String?
    var visible: WizardParameterData.() -> Boolean
    var enabled: WizardParameterData.() -> Boolean
    var default: T?

    fun build(): Parameter<T>

    fun validate() {
        checkNotNull(name) { "Parameter must have a name." }
        checkNotNull(default as Any?) { "Parameter must have a default value." }
    }
}

inline fun stringParameter(block: StringParameterBuilder.() -> Unit): StringParameter =
    StringParameterBuilder().apply(block).build()

inline fun booleanParameter(block: BooleanParameterBuilder.() -> Unit): BooleanParameter =
    BooleanParameterBuilder().apply(block).build()

inline fun <reified T : Enum<T>> enumParameter(
    block: EnumParameterBuilder<T>.() -> Unit
): EnumParameter<T> = enumParameter(T::class, block)

inline fun <T : Enum<T>> enumParameter(
    klass: KClass<T>,
    block: EnumParameterBuilder<T>.() -> Unit,
): EnumParameter<T> = EnumParameterBuilder(klass).apply(block).build()

@TemplateDSL
data class BooleanParameterBuilder(
    override var name: String? = null,
    override var help: String? = null,
    override var visible: WizardParameterData.() -> Boolean = { true },
    override var enabled: WizardParameterData.() -> Boolean = { true },
    override var default: Boolean? = null,
) : ParameterBuilder<Boolean> {
    override fun build(): BooleanParameter {
        validate()
        return BooleanParameter(name!!, help, visible, enabled, default!!)
    }
}

@TemplateDSL
data class StringParameterBuilder(
    override var name: String? = null,
    override var help: String? = null,
    override var visible: WizardParameterData.() -> Boolean = { true },
    override var enabled: WizardParameterData.() -> Boolean = { true },
    override var default: String? = null,
    var constraints: List<Constraint> = listOf(),
    var suggest: WizardParameterData.() -> String? = { null },
    var loggable: Boolean = false,
) : ParameterBuilder<String> {
    override fun build(): StringParameter {
        validate()
        return StringParameter(
            name!!,
            help,
            visible,
            enabled,
            default!!,
            constraints,
            suggest,
            loggable,
        )
    }
}

@TemplateDSL
data class EnumParameterBuilder<T : Enum<T>>(
    val enum: KClass<T>,
    override var name: String? = null,
    override var help: String? = null,
    override var visible: WizardParameterData.() -> Boolean = { true },
    override var enabled: WizardParameterData.() -> Boolean = { true },
    override var default: T? = null,
) : ParameterBuilder<T> {
    override fun build(): EnumParameter<T> {
        validate()
        return EnumParameter(enum, name!!, help, visible, enabled, default!!)
    }
}