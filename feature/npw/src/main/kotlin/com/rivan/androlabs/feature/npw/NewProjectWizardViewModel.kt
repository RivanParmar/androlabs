/*
 * Copyright 2023 Rivan Parmar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.feature.npw

import androidx.lifecycle.ViewModel
import com.rivan.androlabs.wizard.template.api.FormFactor
import com.rivan.androlabs.wizard.template.api.Template
import com.rivan.androlabs.wizard.template.api.WizardUiContext
import com.rivan.androlabs.wizard.template.impl.WizardTemplateProviderImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewProjectWizardViewModel : ViewModel() {

    private val _tabState = MutableStateFlow(
        NpwTabState(
            titles = FormFactor.entries.filterNot { it == FormFactor.Generic },
            currentIndex = 0,
        ),
    )
    val tabState: StateFlow<NpwTabState> = _tabState.asStateFlow()

    fun getTemplates(): List<Template> {
        val templates = WizardTemplateProviderImpl().getTemplates()
            .filter { WizardUiContext.NewProject in it.uiContexts }
        val filteredTemplates = when (tabState.value.currentIndex) {
            0 -> templates.filter { it.formFactor == FormFactor.Mobile }
            1 -> templates.filter { it.formFactor == FormFactor.Wear }
            2 -> templates.filter { it.formFactor == FormFactor.Tv }
            3 -> templates.filter { it.formFactor == FormFactor.Automotive }
            else -> templates
        }

        return sequence {
            yield(Template.NoActivity)
            for (template in filteredTemplates) yield(template)
        }.toList()
    }

    fun switchTab(newIndex: Int) {
        if (newIndex != tabState.value.currentIndex) {
            _tabState.update {
                it.copy(currentIndex = newIndex)
            }
        }
    }
}

data class NpwTabState(
    val titles: List<FormFactor>,
    val currentIndex: Int,
)