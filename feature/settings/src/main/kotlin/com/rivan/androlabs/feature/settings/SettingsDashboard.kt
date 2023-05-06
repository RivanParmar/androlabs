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

package com.rivan.androlabs.feature.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndroLabsTheme
import com.rivan.androlabs.feature.settings.components.SettingsCategory

@Composable
internal fun SettingsRoute(
    onItemClick: (String) -> Unit
) {
    SettingsDashboard(onItemClick)
}

@Composable
fun SettingsDashboard(
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsDashboardCategories.values().forEach { settingsDashboardCategory ->
            val settingsCategoryRoute = settingsDashboardCategory.route
            item(key = settingsCategoryRoute) {
                SettingsCategory(
                    titleRes = settingsDashboardCategory.title,
                    descriptionRes = settingsDashboardCategory.description,
                    icon = settingsDashboardCategory.icon,
                    onClick = { onItemClick(settingsCategoryRoute) }
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsDashboardPreview() {
    AndroLabsTheme {
        SettingsDashboard(onItemClick = {})
    }
}