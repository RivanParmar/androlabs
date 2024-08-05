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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme
import com.rivan.androlabs.core.model.data.UserLabs
import java.io.File

@Composable
fun LabListItem(
    userLab: UserLabs,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = { Text(text = userLab.title) },
        modifier = modifier.clickable {
            onClick()
        },
        supportingContent = {
            val supportingText: String = if (userLab.extraTitle.isNotBlank() &&
                userLab.extraTitle.isNotEmpty()
            ) {
                userLab.extraTitle
            } else {
                userLab.type.serializedName
            }

            Text(text = supportingText)
        },
        leadingContent = {
            if (userLab.iconPath != null) {
                val icon = File(userLab.iconPath!!)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                )
            } else {
                val initials = userLab.title
                    .split(' ', limit = 2)
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .reduce { acc, s -> acc + s }

                // TODO: Use proper background color here
                val color = MaterialTheme.colorScheme.primary

                Text(
                    modifier = Modifier
                        .padding(12.dp)
                        .drawBehind {
                            drawCircle(
                                color = color,
                                radius = this.size.maxDimension,
                            )
                        },
                    text = initials,
                    style = TextStyle(color = Color.White),
                )
            }
        },
        trailingContent = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null)
            }
        },
        colors = ListItemDefaults.colors(
            // TODO: Use a proper color here for background
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    )
}

@Preview
@Composable
private fun ListItemPreview(
    @PreviewParameter(UserLabPreviewParameterProvider::class)
    userLabs: List<UserLabs>,
) {
    AndrolabsTheme {
        LabListItem(
            userLab = userLabs[1],
            onClick = { /*TODO*/ },
        )
    }
}