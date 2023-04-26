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
import com.rivan.androlabs.core.model.data.ProjectTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewProjectWizardViewModel @Inject constructor(

) : ViewModel() {

    private val _tabState = MutableStateFlow(
        NpwTabState(
            titles = listOf(
                R.string.template_phone,
                R.string.template_wear_os,
                R.string.template_android_tv,
                R.string.template_automotive,
                R.string.template_lab
            ),
            currentIndex = 0
        )
    )
    val tabState: StateFlow<NpwTabState> = _tabState.asStateFlow()

    fun switchTab(newIndex: Int) {
        if (newIndex != tabState.value.currentIndex) {
            _tabState.update {
                it.copy(currentIndex = newIndex)
            }
        }
    }
}

data class NpwTabState(
    val titles: List<Int>,
    val currentIndex: Int
)

sealed interface NpwUiState {
    object Loading : NpwUiState

    data class ProjectTemplates(
        val templates: List<ProjectTemplate>
    ) : NpwUiState

    object Empty : NpwUiState
}