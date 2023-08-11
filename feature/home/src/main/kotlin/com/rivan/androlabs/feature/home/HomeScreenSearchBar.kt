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

package com.rivan.androlabs.feature.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.core.designsystem.component.ALDockedSearchBar
import com.rivan.androlabs.core.designsystem.component.ALSearchBar
import com.rivan.androlabs.core.designsystem.icon.ALIcons
import com.rivan.androlabs.core.model.data.ContentType

@Composable
internal fun HomeScreenSearchBar(
    contentType: ContentType,
    text: String,
    active: Boolean,
    onTextChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
) {
    val leadingIcon = @Composable {
        if (!active) {
            Icon(imageVector = ALIcons.Search, contentDescription = null)
        } else {
            IconButton(onClick = { onActiveChange(false) }) {
                Icon(imageVector = ALIcons.Back, contentDescription = null)
            }
        }
    }

    val trailingIcon = @Composable {
        if (!active) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = ALIcons.Account, contentDescription = null)
            }
        }
    }

    if (contentType == ContentType.SINGLE_PANE) {
        ALSearchBar(
            placeholderRes = R.string.search_bar_placeholder_text,
            text = text,
            active = active,
            onTextChange = onTextChange,
            onActiveChange = onActiveChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        ) {

        }
    } else {
        ALDockedSearchBar(
            placeholderRes = R.string.search_bar_placeholder_text,
            text = text,
            active = active,
            onTextChange = onTextChange,
            onActiveChange = onActiveChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            modifier = Modifier.padding(12.dp),
        ) {

        }
    }
}