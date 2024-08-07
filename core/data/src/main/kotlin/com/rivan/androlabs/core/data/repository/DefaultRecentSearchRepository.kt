/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.data.model.RecentSearchQuery
import com.rivan.androlabs.core.data.model.asExternalModel
import com.rivan.androlabs.core.database.dao.RecentSearchQueryDao
import com.rivan.androlabs.core.database.model.RecentSearchQueryEntity
import com.rivan.androlabs.core.network.AndrolabsDispatcher.IO
import com.rivan.androlabs.core.network.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import javax.inject.Inject

class DefaultRecentSearchRepository @Inject constructor(
    private val recentSearchQueryDao: RecentSearchQueryDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : RecentSearchRepository {

    override suspend fun insertOrReplaceRecentSearch(searchQuery: String) {
        withContext(ioDispatcher) {
            recentSearchQueryDao.insertOrReplaceRecentSearchQuery(
                RecentSearchQueryEntity(
                    query = searchQuery,
                    queriedDate = Clock.System.now(),
                ),
            )
        }
    }

    override suspend fun clearRecentSearch(searchQuery: String) {
        // TODO: We may not need Coroutine Scope here
        withContext(ioDispatcher) {
            recentSearchQueryDao.clearRecentSearchQuery(searchQuery)
        }
    }

    override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> =
        recentSearchQueryDao.getRecentSearchQueryEntities(limit).map { searchQueries ->
            searchQueries.map {
                it.asExternalModel()
            }
        }

    override suspend fun clearRecentSearches() = recentSearchQueryDao.clearRecentSearchQueries()
}