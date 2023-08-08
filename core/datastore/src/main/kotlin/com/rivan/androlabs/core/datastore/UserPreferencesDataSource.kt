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

package com.rivan.androlabs.core.datastore

import androidx.datastore.core.DataStore
import com.rivan.androlabs.core.model.data.DarkThemeConfig
import com.rivan.androlabs.core.model.data.ThemeType
import com.rivan.androlabs.core.model.data.UserSettings
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data
        .map {
            UserSettings(
                themeType = when (it.themeType) {
                    null,
                    ThemeTypeProto.THEME_TYPE_UNSPECIFIED,
                    ThemeTypeProto.UNRECOGNIZED,
                    ThemeTypeProto.THEME_TYPE_DEFAULT,
                    -> ThemeType.DEFAULT
                    ThemeTypeProto.THEME_TYPE_CUSTOM -> ThemeType.CUSTOM
                },
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                    ->
                        DarkThemeConfig.FOLLOW_SYSTEM
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                        DarkThemeConfig.LIGHT
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                },
                useDynamicColor = it.useDynamicColor,

                savePath = it.savePath
            )
        }

    suspend fun setThemeType(themeType: ThemeType) {
        userPreferences.updateData {
            it.copy {
                this.themeType = when (themeType) {
                    ThemeType.DEFAULT -> ThemeTypeProto.THEME_TYPE_DEFAULT
                    ThemeType.CUSTOM -> ThemeTypeProto.THEME_TYPE_CUSTOM
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicColor = useDynamicColor
            }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setSavePath(savePath: String) {
        userPreferences.updateData {
            it.copy {
                this.savePath = savePath
            }
        }
    }
}