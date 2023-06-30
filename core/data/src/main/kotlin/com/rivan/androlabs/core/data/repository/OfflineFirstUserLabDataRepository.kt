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

package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.datastore.UserLabPrefsDataSource
import com.rivan.androlabs.core.model.data.UserLabData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserLabDataRepository @Inject constructor(
    private val prefsDataSource: UserLabPrefsDataSource
) : UserLabDataRepository {

    override val userLabData: Flow<UserLabData> =
        prefsDataSource.userLabData

    override suspend fun setRecentLabIds(recentLabIds: Set<String>) =
        prefsDataSource.setRecentLabIds(recentLabIds)

    override suspend fun toggleRecentLabId(
        labId: String, recent: Boolean
    ) {
        prefsDataSource.toggleRecentLabId(labId, recent)
    }

    override suspend fun updateLabFavourite(
        labId: String,
        favourite: Boolean
    ) {
        prefsDataSource.toggleLabFavourite(labId, favourite)
    }

    override suspend fun updateLabCompleted(
        labId: String,
        completed: Boolean
    ) {
        prefsDataSource.toggleLabCompleted(labId, completed)
    }

}