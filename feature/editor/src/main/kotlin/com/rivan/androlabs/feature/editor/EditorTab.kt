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

package com.rivan.androlabs.feature.editor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.theme.AndrolabsTheme

// TODO: Optimize if possible
@Composable
internal fun EditorTab(
    selected: Boolean,
    fileIcon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit,
    onCloseButtonClick: () -> Unit,
    text: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxHeight(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(IntrinsicSize.Min)
            ) {
                Icon(
                    imageVector = fileIcon,
                    contentDescription = null,
                    modifier = Modifier.alignBy { it.measuredHeight },
                )

                Spacer(modifier = Modifier.width(8.dp))

                val style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
                ProvideTextStyle(
                    value = style,
                    content = text,
                )
            }

            IconButton(
                onClick = onCloseButtonClick,
                enabled = selected,
            ) {
                AnimatedContent(
                    targetState = selected,
                    label = "Editor Tab Close Button Animation"
                ) {
                    if (it) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null,
                            modifier = Modifier.alignByBaseline()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .alignByBaseline()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditorTabPreview() {
    AndrolabsTheme {
        Surface {
            var selectedTabIndex by remember { mutableIntStateOf(0) }

            SecondaryScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
            ) {
                EditorTab(
                    selected = selectedTabIndex == 0,
                    fileIcon = Icons.Outlined.Android,
                    onClick = { selectedTabIndex = 0 },
                    onCloseButtonClick = {}
                ) {
                    Text(text = "File1")
                }

                EditorTab(
                    selected = selectedTabIndex == 1,
                    fileIcon = Icons.Outlined.Android,
                    onClick = { selectedTabIndex = 1 },
                    onCloseButtonClick = {}
                ) {
                    Text(text = "File2")
                }
            }
        }
    }
}