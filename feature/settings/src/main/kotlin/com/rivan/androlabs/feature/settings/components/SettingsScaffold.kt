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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.rivan.androlabs.core.designsystem.component.ALScaffold
import com.rivan.androlabs.core.designsystem.component.ALTopAppBarMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScaffold(
    titleRes: Int,
    navigationIcon: ImageVector = Icons.Outlined.ArrowBack,
    navigationIconContentDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    onBackClick: () -> Unit,
    onActionClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    /*Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ALTopAppBarMedium(
                titleRes = titleRes,
                navigationIcon = navigationIcon,
                navigationIconContentDescription = navigationIconContentDescription,
                actionIcon = actionIcon,
                actionIconContentDescription = actionIconContentDescription,
                scrollBehavior = scrollBehavior,
                onNavigationClick = onBackClick,
                onActionClick = onActionClick,
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = Color.Transparent
    ) {
        content(it)
    }*/

    ALScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Transparent,
        topAppBar = {
            ALTopAppBarMedium(
                titleRes = titleRes,
                navigationIcon = navigationIcon,
                navigationIconContentDescription = navigationIconContentDescription,
                actionIcon = actionIcon,
                actionIconContentDescription = actionIconContentDescription,
                scrollBehavior = scrollBehavior,
                onNavigationClick = onBackClick,
                onActionClick = onActionClick,
            )
        }
    ) {
        content(it)
    }
}