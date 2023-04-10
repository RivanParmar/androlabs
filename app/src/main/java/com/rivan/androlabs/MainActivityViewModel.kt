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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivan.androlabs.MainActivityUiState.Loading
import com.rivan.androlabs.MainActivityUiState.Success
import com.rivan.androlabs.core.data.repository.UserProjectResourceDataRepository
import com.rivan.androlabs.core.model.data.UserProjectResourceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userProjectResourceDataRepository: UserProjectResourceDataRepository
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> =
        userProjectResourceDataRepository.userProjectResourceData.map {
            Success(it)
        }.stateIn(
            scope = viewModelScope,
            initialValue = Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )
}

sealed interface MainActivityUiState {
    object Loading: MainActivityUiState
    data class Success(val userProjectResourceData: UserProjectResourceData) : MainActivityUiState
}