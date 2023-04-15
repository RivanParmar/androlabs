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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.designsystem.icon.Icon.ImageVectorIcon

@Composable
fun RootSettingsListItem(
    titleId: Int,
    supportingTextId: Int,
    icon: ImageVectorIcon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    ListItem(
        headlineText = {
            Text(
                text = stringResource(id = titleId),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        supportingText = {
            Text(
                text = stringResource(id = supportingTextId),
                style = MaterialTheme.typography.bodySmall
            )
        },
        modifier = modifier.clickable { onClick() },
        leadingContent = {
            // TODO: Add tint to the icon as well as to the background that changes dynamically on
            //  wallpaper change
            Icon(
                imageVector = icon.imageVector,
                contentDescription = null,
                modifier = iconModifier
                    .background(
                        MaterialTheme.colorScheme.surface,
                        CircleShape
                    )
                    .padding(4.dp)
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun SampleItem() {
    RootSettingsListItem(
        titleId = R.string.settings_general,
        supportingTextId = R.string.settings_general_desc,
        icon = ImageVectorIcon(ALIcons.GeneralSettings),
        onClick = { /*TODO*/ }
    )
}