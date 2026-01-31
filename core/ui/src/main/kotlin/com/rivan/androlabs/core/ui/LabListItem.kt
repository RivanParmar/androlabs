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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.rivan.androlabs.core.model.data.Lab
import java.io.File

// TODO: The padding between the leading content and the title text is variable.
//  Fix this!
@Composable
fun LabListItem(
    lab: Lab,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }

    ListItem(
        headlineContent = { Text(text = lab.title) },
        modifier = modifier.clickable {
            onClick()
        },
        supportingContent = {
            val supportingText: String = if (lab.extraTitle.isNotBlank() &&
                lab.extraTitle.isNotEmpty()
            ) {
                lab.extraTitle
            } else {
                lab.type.serializedName
            }

            Text(text = supportingText)
        },
        leadingContent = {
            if (lab.iconPath != null) {
                val icon = File(lab.iconPath!!)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                )
            } else {
                val initials = lab.title
                    .split(' ', limit = 1)
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
            IconButton(onClick = { isDropdownMenuExpanded = true }) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = isDropdownMenuExpanded,
                onDismissRequest = { isDropdownMenuExpanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Open") },
                    onClick = {
                        isDropdownMenuExpanded = false
                        onClick()
                    },
                )
                DropdownMenuItem(
                    text = { Text(text = "Set Custom Icon") },
                    onClick = { /*TODO*/ },
                )
                DropdownMenuItem(
                    text = { Text(text = "Remove from Recent") },
                    onClick = { /*TODO*/ },
                )
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
    @PreviewParameter(LabPreviewParameterProvider::class)
    labs: List<Lab>,
) {
    AndrolabsTheme {
        LabListItem(
            lab = labs[1],
            onClick = {},
        )
    }
}