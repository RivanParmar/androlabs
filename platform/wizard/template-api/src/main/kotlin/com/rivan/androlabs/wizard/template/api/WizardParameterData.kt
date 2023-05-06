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
 * Data used for rendering template UI, provided by the wizards
 *
 * Useful when rendering UI according to templates (e.g. visibility may depend on [isNewModule]).
 * It is not passed to the template renderer, in contrast to [TemplateData].
 *
 * Note: it updates [Parameter.wizardParameterData] for all [consumers].
 */
data class WizardParameterData(
    val packageName: String,
    val isNewModule: Boolean,
    /**
     * Name of the source provider.
     * Also known as "flavor". E.g. "main", "debug" etc.
     */
    var sourceProviderName: String,
    private val consumers: Collection<Parameter<*>>
) {
    init {
        consumers.forEach { it.wizardParameterData = this }
    }
}