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

package com.rivan.androlabs.core.database

import com.rivan.androlabs.core.database.dao.LabDao
import com.rivan.androlabs.core.database.dao.LabsFtsDao
import com.rivan.androlabs.core.database.dao.RecentSearchQueryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesLabDao(
        database: AndrolabsDatabase,
    ): LabDao = database.labDao()

    @Provides
    fun providesLabFtsDao(
        database: AndrolabsDatabase,
    ): LabsFtsDao = database.labFtsDao()

    @Provides
    fun providesRecentSearchQueryDao(
        database: AndrolabsDatabase,
    ): RecentSearchQueryDao = database.recentSearchQueryDao()
}