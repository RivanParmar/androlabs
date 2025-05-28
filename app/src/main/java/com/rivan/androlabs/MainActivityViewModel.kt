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

package com.rivan.androlabs

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivan.androlabs.MainActivityUiState.Loading
import com.rivan.androlabs.core.data.repository.LabRepository
import com.rivan.androlabs.core.data.repository.UserSettingsRepository
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.model.data.LabType
import com.rivan.androlabs.core.model.data.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
//    private val userLabDataRepository: UserLabDataRepository,
    private val labRepository: LabRepository,
    private val userSettingsRepository: UserSettingsRepository,
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> =
        /*combine(
            userLabDataRepository.userLabData,
            userSettingsRepository.userSettings
        ) { userLabData, userSettings ->
            Success(userLabData, userSettings)
        }.stateIn(
            scope = viewModelScope,
            initialValue = Loading,
            started = SharingStarted.Eagerly
        )*/
        userSettingsRepository.userSettings
            .map(MainActivityUiState::Success)
            .stateIn(
                scope = viewModelScope,
                initialValue = Loading,
                started = SharingStarted.Eagerly,
            )

    fun updateSavePath(savePath: String) {
        viewModelScope.launch {
            userSettingsRepository.setSavePath(savePath)
        }
    }

    fun openProject(path: String) {
        viewModelScope.launch {
            val uri = path.toUri()
            val folderName = getFolderNameFromUri(uri)

            // TODO: This is incomplete! We need to retrieve all the information
            //  from the project and update the entry.
            if (folderName != null) {
                labRepository.insertOrIgnoreLabs(
                    listOf(
                        Lab(
                            0,
                            folderName,
                            "Project",
                            "",
                            null,
                            null,
                            null,
                            null,
                            path,
                            LabType.Unknown,
                            null,
                        ),
                    ),
                )
            }
        }
    }

    private fun getFolderNameFromUri(uri: Uri): String? {
        return DocumentFile.fromTreeUri(context, uri)?.name
    }
}

sealed interface MainActivityUiState {
    data object Loading: MainActivityUiState
    data class Success(
//        val userLabData: UserLabData,
        val userSettings: UserSettings
    ) : MainActivityUiState
}