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

import com.rivan.androlabs.core.datastore.UserPreferencesDataSource
import com.rivan.androlabs.core.model.data.DarkThemeConfig
import com.rivan.androlabs.core.model.data.ThemeType
import com.rivan.androlabs.core.model.data.UserSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) : UserSettingsRepository {

    override val userSettings: Flow<UserSettings> =
        userPreferencesDataSource.userData

    override suspend fun setThemeType(themeType: ThemeType) {
        userPreferencesDataSource.setThemeType(themeType)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

}