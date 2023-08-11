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

package com.rivan.androlabs.core.data.di

import com.rivan.androlabs.core.data.repository.DefaultRecentSearchRepository
import com.rivan.androlabs.core.data.repository.LabRepository
import com.rivan.androlabs.core.data.repository.OfflineFirstLabRepository
import com.rivan.androlabs.core.data.repository.OfflineFirstUserLabDataRepository
import com.rivan.androlabs.core.data.repository.RecentSearchRepository
import com.rivan.androlabs.core.data.repository.UserLabDataRepository
import com.rivan.androlabs.core.data.repository.UserSettingsRepository
import com.rivan.androlabs.core.data.repository.UserSettingsRepositoryImpl
import com.rivan.androlabs.core.data.util.ConnectivityManagerNetworkMonitor
import com.rivan.androlabs.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsLabRepository(
        labRepository: OfflineFirstLabRepository,
    ): LabRepository

    @Binds
    fun bindsUserLabDataRepository(
        userLabDataRepository: OfflineFirstUserLabDataRepository,
    ): UserLabDataRepository

    @Binds
    fun bindsUserSettingsRepository(
        userSettingsRepository: UserSettingsRepositoryImpl,
    ): UserSettingsRepository

    @Binds
    fun bindsRecentSearchRepository(
        recentSearchRepository: DefaultRecentSearchRepository,
    ): RecentSearchRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}