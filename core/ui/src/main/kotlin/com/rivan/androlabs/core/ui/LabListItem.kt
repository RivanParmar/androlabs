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

package com.rivan.androlabs.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.rivan.androlabs.core.designsystem.R.drawable.*
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.domain.model.UserLabs

@Composable
fun LabListItem(
    userLabs: UserLabs,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = { Text(text = userLabs.title) },
        modifier = modifier.clickable {
                onClick()
            },
        supportingContent = {
            val supportingText: String = if (userLabs.extraTitle.isNotBlank() &&
                userLabs.extraTitle.isNotEmpty()) {
                userLabs.extraTitle
            } else {
                userLabs.type.serializedName
            }

            Text(text = supportingText)
        },
        leadingContent = {
            // TODO: Use AsyncImage here to load images
            Image(imageVector = Icons.Outlined.Star, contentDescription = null)
        },
        trailingContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null)
            }
        },
        colors = ListItemDefaults.colors(
            // TODO: Use a proper color here for background
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Preview
@Composable
private fun ListItemPreview(
    @PreviewParameter(UserLabPreviewParameterProvider::class)
    userLabs: List<UserLabs>
) {
    AndrolabsTheme {
        LabListItem(
            userLabs = userLabs[1],
            onClick = { /*TODO*/ })
    }
}