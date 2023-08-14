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

package com.rivan.androlabs.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

/**
 * Androlabs search bar with content slots. Wraps Material 3 [SearchBar].
 *
 * @param placeholderRes String resource identifier for the placeholder text.
 * @param modifier Modifier to be applied to the search bar.
 * @param text The text inputted by the user.
 * @param active Whether the search bar is currently focused or not.
 * @param onTextChange The callback to be invoked when the input text changes.
 * @param onActiveChange The callback to be invoked when the focus of the search bar changes.
 * @param leadingIcon The icon to be shown at the beginning of the search bar container. By default
 * no icon is shown.
 * @param trailingIcon The icon to be shown at the end of the search bar. By default no icon is
 * shown.
 * @param content The content to be shown when the search bar is in focus.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ALSearchBar(
    @StringRes placeholderRes: Int,
    modifier: Modifier = Modifier,
    text: String = "",
    active: Boolean = false,
    onSearch: (String) -> Unit,
    onTextChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val horizontalPadding: Dp by animateDpAsState(
        if (active) {
            0.dp
        } else {
            16.dp
        },
        label = "SearchBar Horizontal Padding Animation"
    )

    Box(
        modifier = Modifier
            .semantics { isContainer = true }
            .zIndex(1f)
    ) {
        SearchBar(
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            query = text,
            onQueryChange = { onTextChange(it) },
            onSearch = {
                onSearch(it)
                onActiveChange(false)
            },
            active = active,
            onActiveChange = { onActiveChange(it) },
            placeholder = { Text(text = stringResource(id = placeholderRes)) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            content = content
        )
    }
}

/**
 * Androlabs docked search bar with content slots. Wraps Material 3 [DockedSearchBar].
 *
 * @param placeholderRes String resource identifier for the placeholder text.
 * @param modifier Modifier to be applied to the search bar.
 * @param text The text inputted by the user.
 * @param active Whether the search bar is currently focused or not.
 * @param onTextChange The callback to be invoked when the input text changes.
 * @param onActiveChange The callback to be invoked when the focus of the search bar changes.
 * @param leadingIcon The icon to be shown at the beginning of the search bar container. By default
 * no icon is shown.
 * @param trailingIcon The icon to be shown at the end of the search bar. By default no icon is
 * shown.
 * @param content The content to be shown when the search bar is in focus.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ALDockedSearchBar(
    @StringRes placeholderRes: Int,
    modifier: Modifier = Modifier,
    text: String = "",
    active: Boolean = false,
    onSearch: (String) -> Unit,
    onTextChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .semantics { isContainer = true }
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        DockedSearchBar(
            modifier = modifier.align(Alignment.TopEnd),
            query = text,
            onQueryChange = { onTextChange(it) },
            onSearch = {
                onSearch(it)
                onActiveChange(false)
            },
            active = active,
            onActiveChange = { onActiveChange(it) },
            placeholder = { Text(text = stringResource(id = placeholderRes)) },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            content = content
        )
    }
}