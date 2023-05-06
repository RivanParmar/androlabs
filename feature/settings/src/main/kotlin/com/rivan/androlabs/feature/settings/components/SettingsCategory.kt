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

package com.rivan.androlabs.feature.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.icon.Icon.ImageVectorIcon

@Composable
fun SettingsCategory(
    titleRes: Int,
    descriptionRes: Int? = null,
    icon: ImageVectorIcon,
    onClick: () -> Unit
) {
    SettingsItemTemplate(
        modifier = Modifier.clickable(onClick = onClick),
        title = {
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        description = {
            if (descriptionRes != null) {
                Text(
                    text = stringResource(id = descriptionRes),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        startWidget = {
            Icon(
                imageVector = icon.imageVector,
                contentDescription = null,
                modifier = Modifier
                    .background(
                        // TODO: Change this color when we add the final theme
                        MaterialTheme.colorScheme.surface,
                        CircleShape
                    )
                    .padding(4.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        containerColor = Color.Transparent
    )
}