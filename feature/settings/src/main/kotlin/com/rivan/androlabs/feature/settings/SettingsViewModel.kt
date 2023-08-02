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

package com.rivan.androlabs.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivan.androlabs.core.data.repository.UserSettingsRepository
import com.rivan.androlabs.core.model.data.DarkThemeConfig
import com.rivan.androlabs.core.model.data.ThemeType
import com.rivan.androlabs.feature.settings.SettingsUiState.Loading
import com.rivan.androlabs.feature.settings.SettingsUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {
    val settingsUiState: StateFlow<SettingsUiState> =
        userSettingsRepository.userSettings
            .map { userSettings ->
                Success(
                    preferences = Preferences(
                        type = userSettings.themeType,
                        useDynamicColor = userSettings.useDynamicColor,
                        darkThemeConfig = userSettings.darkThemeConfig
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Loading
            )

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userSettingsRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }
}

data class Preferences(
    val type: ThemeType,
    val useDynamicColor: Boolean,
    val darkThemeConfig: DarkThemeConfig
)

sealed interface SettingsUiState {
    object Loading : SettingsUiState

    data class Success(val preferences: Preferences) : SettingsUiState
}