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

package com.rivan.androlabs.feature.npw

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.wizard.template.api.Template

@Composable
fun TemplatesTabContent(
    templates: List<Template>,
    onTemplateClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedIndex: Int = -1,
) {
    if (templates.isEmpty()) {
        EmptyTabContent()
    } else {
        LazyVerticalGrid(
            modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            columns = GridCells.Adaptive(172.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            templates.forEachIndexed { index, template ->
                //val templateName = template.name
                item(key = index) {
                    TemplateCard(
                        template = template,
                        onClick = { onTemplateClick(index) },
                        selected = index == selectedIndex,
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyTabContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "No templates available!")
    }
}