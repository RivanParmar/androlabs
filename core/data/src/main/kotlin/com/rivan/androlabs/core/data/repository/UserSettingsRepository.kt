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

import com.rivan.androlabs.core.model.data.DarkThemeConfig
import com.rivan.androlabs.core.model.data.ThemeType
import com.rivan.androlabs.core.model.data.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

    /**
     * Stream of [UserSettings].
     */
    val userSettings: Flow<UserSettings>

    /**
     * Sets the desired theme type.
     */
    suspend fun setThemeType(themeType: ThemeType)

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets the preferred dynamic color config.
     */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    /**
     * Sets the common path for saving the labs.
     */
    suspend fun setSavePath(savePath: String)

    /**
     * Sets the preferred lite mode config.
     */
    suspend fun setLiteModePreference(liteModeEnabled: Boolean)
}