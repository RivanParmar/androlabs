/*
 * Copyright 2025 Rivan Parmar
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

package com.rivan.androlabs.feature.npw

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.rivan.androlabs.core.designsystem.component.ALScrollableTabRow
import com.rivan.androlabs.core.designsystem.component.ALTab
import com.rivan.androlabs.core.designsystem.component.ALTabRow
import com.rivan.androlabs.wizard.template.api.FormFactor
import com.rivan.androlabs.wizard.template.api.Template

/**
 * First screen in the New Project wizard that allows user to select the [FormFactor]
 * (Mobile, Wear, TV, etc.) and its template ("Empty Activity", "Basic",
 * "Navigation Drawer", etc.).
 */
@Composable
internal fun ChooseAndroidProjectStep(
    tabState: NpwTabState,
    templates: List<Template>,
    switchTab: (Int) -> Unit,
    canGoForward: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shouldUseScrollableTabRow: Boolean = true,
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

    ChooseAndroidProjectStepContent(
        tabState = tabState,
        templates = templates,
        switchTab = switchTab,
        onTemplateClick = { index ->
            selectedIndex = if (selectedIndex != index) index else -1
            canGoForward(selectedIndex != -1)
        },
        modifier = modifier,
        selectedIndex = selectedIndex,
        shouldUseScrollableTabRow = shouldUseScrollableTabRow,
    )
}

@Composable
private fun ChooseAndroidProjectStepContent(
    tabState: NpwTabState,
    templates: List<Template>,
    switchTab: (Int) -> Unit,
    onTemplateClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedIndex: Int = -1,
    shouldUseScrollableTabRow: Boolean = true,
) {
    val tabs: @Composable () -> Unit = {
        tabState.titles.forEachIndexed { index, titleId ->
            ALTab(
                selected = index == tabState.currentIndex,
                onClick = {
                    switchTab(index)
                    // Deselect any selected template when the user switches tabs.
                    onTemplateClick(-1)
                },
                text = { Text(text = titleId.displayName) },
            )
        }
    }

    Column(modifier) {
        if (shouldUseScrollableTabRow) {
            ALScrollableTabRow(
                selectedTabIndex = tabState.currentIndex,
                modifier = Modifier.fillMaxWidth(),
                tabs = tabs,
            )
        } else {
            ALTabRow(
                selectedTabIndex = tabState.currentIndex,
                modifier = Modifier.fillMaxWidth(),
                tabs = tabs,
            )
        }

        TemplatesTabContent(
            templates = templates,
            onTemplateClick = onTemplateClick,
            selectedIndex = selectedIndex,
        )
    }
}