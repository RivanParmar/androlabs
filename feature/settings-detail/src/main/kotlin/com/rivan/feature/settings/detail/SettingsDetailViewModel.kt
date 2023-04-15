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

package com.rivan.feature.settings.detail

import androidx.lifecycle.ViewModel
import com.rivan.androlabs.core.data.repository.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsDetailViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {
    /*val settingsUiState: StateFlow<SettingsUiState> =
        userSettingsRepository.userSettings
            .map { userSettings ->
                SettingsUiState.Success(
                    settings = UserEditableSettings(
                        type = userSettings.themeType,
                        useDynamicColor = userSettings.useDynamicColor,
                        darkThemeConfig = userSettings.darkThemeConfig
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState.Loading
            )*/
}

/*sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: EditableSettings) : SettingsUiState
}*/