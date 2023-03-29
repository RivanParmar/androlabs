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

import com.rivan.androlabs.core.datastore.UserProjectResourcePrefsDataSource
import com.rivan.androlabs.core.model.data.UserProjectResourceData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserProjectResourceDataRepository @Inject constructor(
    private val prefsDataSource: UserProjectResourcePrefsDataSource
) : UserProjectResourceDataRepository {

    override val userProjectResourceData: Flow<UserProjectResourceData> =
        prefsDataSource.userProjectResourceData

    override suspend fun setRecentProjectResourceIds(recentProjectResourceIds: Set<String>) =
        prefsDataSource.setRecentProjectResourceIds(recentProjectResourceIds)

    override suspend fun toggleRecentProjectResourceId(
        projectResourceId: String, recent: Boolean
    ) {
        prefsDataSource.toggleRecentProjectResourceId(projectResourceId, recent)
    }

    override suspend fun updateProjectResourceFavourite(
        projectResourceId: String,
        favourite: Boolean
    ) {
        prefsDataSource.toggleProjectResourceFavourite(projectResourceId, favourite)
    }

    override suspend fun updateProjectResourceCompleted(
        projectResourceId: String,
        completed: Boolean
    ) {
        prefsDataSource.toggleProjectResourceCompleted(projectResourceId, completed)
    }

}