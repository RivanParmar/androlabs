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

package com.rivan.androlabs.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivan.androlabs.core.data.repository.LabQuery
import com.rivan.androlabs.core.data.repository.RecentSearchRepository
import com.rivan.androlabs.core.data.repository.UserLabDataRepository
import com.rivan.androlabs.core.data.util.SyncStatusMonitor
import com.rivan.androlabs.core.domain.GetRecentSearchQueriesUseCase
import com.rivan.androlabs.core.domain.GetUserLabsUseCase
import com.rivan.androlabs.core.model.data.ContentType
import com.rivan.androlabs.core.model.data.UserLabData
import com.rivan.androlabs.core.model.data.UserLabs
import com.rivan.androlabs.core.ui.LabFeedUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    syncStatusMonitor: SyncStatusMonitor,
    getRecentUserLabs: GetUserLabsUseCase,
    recentSearchQueriesUseCase: GetRecentSearchQueriesUseCase,
    private val userLabDataRepository: UserLabDataRepository,
    private val recentSearchRepository: RecentSearchRepository,
) : ViewModel() {

    val isSyncing = syncStatusMonitor.isSyncing
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    private val _labFeedUiState = MutableStateFlow(LabFeedUIState(loading = true))
    val labFeedUiState: StateFlow<LabFeedUIState> = _labFeedUiState

    val recentSearchQueriesUiState: StateFlow<RecentSearchQueriesUiState> =
        recentSearchQueriesUseCase().map(RecentSearchQueriesUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RecentSearchQueriesUiState.Loading,
            )

    init {
        viewModelScope.launch {
            userLabDataRepository.getRecentLabs(getRecentUserLabs)
                .catch { ex ->
                    _labFeedUiState.value = LabFeedUIState(error = ex.message)
                }
                .collect { labs ->
                    _labFeedUiState.value = LabFeedUIState(
                        labs = labs,
                        selectedLab = null,
                    )
                }
        }
    }

    fun setSelectedLab(labId: String, contentType: ContentType) {
        val lab = labFeedUiState.value.labs.find { it.id == labId }
        _labFeedUiState.value = _labFeedUiState.value.copy(
            selectedLab = lab,
            isDetailOnlyOpen = contentType == ContentType.SINGLE_PANE,
        )
    }

    fun closeDetailScreen() {
        _labFeedUiState.value = _labFeedUiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedLab = null,
            )
    }

    fun updateRecentLab(labId: String, isRecent: Boolean) {
        viewModelScope.launch {
            userLabDataRepository.toggleRecentLabId(
                labId, isRecent
            )
        }
    }

    fun updateLabFavourited(labId: String, isChecked: Boolean) {
        viewModelScope.launch {
            userLabDataRepository
                .updateLabFavourite(labId, isChecked)
        }
    }

    fun updateLabCompleted(labId: String, isChecked: Boolean) {
        viewModelScope.launch {
            userLabDataRepository
                .updateLabCompleted(labId, isChecked)
        }
    }

    fun onSearchTriggered(query: String) {
        viewModelScope.launch {
            recentSearchRepository.insertOrReplaceRecentSearch(query)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun UserLabDataRepository.getRecentLabs(
    getUserLabs: GetUserLabsUseCase,
): Flow<List<UserLabs>> = userLabData
    // Map the user data into a set of recent lab IDs or null if we should return an
    // empty list.
    .map { userLabData ->
        if (userLabData.shouldShowEmptyFeed()) {
            null
        } else {
            userLabData.recentLabs
        }
    }
    // Only emit a set of recent lab IDs if it's changed. This avoids calling
    // potentially expensive operations (like setting up a new flow) when nothing has changed.
    .distinctUntilChanged()
    // getUserLabs returns a flow, so we have a flow inside a flow. flatMapLatest moves
    // the inner flow (the one we want to return) to the outer flow and cancels any previous flows
    // created by getUserLabs.
    .flatMapLatest { recentLabs ->
        if (recentLabs == null) {
            flowOf(emptyList())
        } else {
            getUserLabs(
                LabQuery(
                    filterLabIds = recentLabs,
                )
            )
        }
    }

private fun UserLabData.shouldShowEmptyFeed() =
    recentLabs.isEmpty()