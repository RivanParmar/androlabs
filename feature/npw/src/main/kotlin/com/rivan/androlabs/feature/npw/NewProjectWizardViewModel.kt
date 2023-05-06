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
import com.rivan.androlabs.wizard.template.api.Template
import com.rivan.androlabs.wizard.template.api.TemplateCategory
import com.rivan.androlabs.wizard.template.impl.WizardTemplateProviderImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewProjectWizardViewModel : ViewModel() {

    private val _tabState = MutableStateFlow(
        NpwTabState(
            titles = TemplateCategory.values().asList(),
            currentIndex = 0
        )
    )
    val tabState: StateFlow<NpwTabState> = _tabState.asStateFlow()

    fun getTemplates(): List<Template> {
        // TODO: Optimize this more if possible
        val templates = WizardTemplateProviderImpl().getTemplates()
        val filteredTemplates = when (tabState.value.currentIndex) {
            0 -> templates.filter { it.templateCategory == TemplateCategory.Mobile }
            1 -> templates.filter { it.templateCategory == TemplateCategory.Wear }
            2 -> templates.filter { it.templateCategory == TemplateCategory.Tv }
            3 -> templates.filter { it.templateCategory == TemplateCategory.Automotive }
            4 -> templates.filter { it.templateCategory == TemplateCategory.Lab }
            else -> templates
        }
        // If currently selected tab is 'Lab', then do not return `Template.NoActivity`
        return if (tabState.value.currentIndex != 4) {
            sequence {
                yield(Template.NoActivity)
            }.toList() + filteredTemplates
        } else filteredTemplates
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
    val titles: List<TemplateCategory>,
    val currentIndex: Int
)