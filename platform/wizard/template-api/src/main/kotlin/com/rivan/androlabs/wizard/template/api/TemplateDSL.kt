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

import java.io.File

internal data class TemplateImpl(
    override val name: String,
    override val description: String,
    override val documentationUrl: String?,
    override val minSdk: Int,
    override val category: Category,
    override val formFactor: FormFactor,
    override val widgets: Collection<Widget<*>>,
    private val _thumb: () -> Thumb,
    override val recipe: Recipe,
    override val uiContexts: Collection<WizardUiContext>,
    override val constraints: Collection<TemplateConstraint>,
    override val useGenericInstrumentedTests: Boolean,
    override val useGenericLocalTests: Boolean,
) : Template {
    override fun thumb(): Thumb = _thumb()
}

@DslMarker
annotation class TemplateDSL

inline fun template(block: TemplateBuilder.() -> Unit): Template =
    TemplateBuilder().apply(block).build()

@TemplateDSL
class TemplateBuilder {
    var name: String? = null
    var description: String? = null
    var documentationUrl: String? = null
    var minApi: Int = 1
    var category: Category? = null
    var formFactor: FormFactor? = null
    var thumb: () -> Thumb = { Thumb.NoThumb }
    var recipe: Recipe? = null
    var screens: Collection<WizardUiContext> = listOf()
    var widgets = listOf<Widget<*>>()
    var constraints = listOf<TemplateConstraint>()
    var useGenericAndroidTests: Boolean = true
    var useGenericLocalTests: Boolean = true

    fun widgets(vararg widgets: Widget<*>) {
        this.widgets = widgets.toList()
    }

    @TemplateDSL
    class ThumbBuilder

    /**
     * A wrapper for collection of [Thumb]s with an optional [get]ter. Implementations usually use
     * [Parameter.value] to choose [Thumb].
     */
    fun thumb(block: ThumbBuilder.() -> File) {
        thumb = { Thumb { findResource(this.javaClass, ThumbBuilder().block()) } }
    }

    @PublishedApi
    internal fun build(): Template {
        checkNotNull(name) { "Template must have a name." }
        checkNotNull(description) { "Template must have a description." }
        checkNotNull(category) { "Template have to specify category." }
        checkNotNull(formFactor) { "Template have to specify form factor." }
        checkNotNull(recipe) { "Template must have a recipe to run." }

        return TemplateImpl(
            name!!,
            description!!,
            documentationUrl,
            minApi,
            category!!,
            formFactor!!,
            widgets,
            thumb,
            recipe!!,
            screens,
            constraints,
            useGenericAndroidTests,
            useGenericLocalTests,
        )
    }
}