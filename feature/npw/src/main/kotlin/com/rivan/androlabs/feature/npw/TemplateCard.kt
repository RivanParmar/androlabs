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

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rivan.androlabs.wizard.template.api.Template

@Composable
fun TemplateCard(
    template: Template,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    // TODO: Maybe use an `OutlinedCard` here and change the look of the template
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = if (selected) 2.dp else 0.dp,
                // TODO: Change this to a better color
                color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .selectable(
                selected = selected,
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                TemplateIcon(template = template)
            }

            Box(
                modifier = Modifier.padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TemplateTitle(title = template.name)
                }
            }
        }
    }
}

@Composable
fun TemplateIcon(template: Template) {
    if (template == Template.NoActivity) {
        Image(
            // TODO: Convert `no_activity` to vector
            painter = painterResource(id = R.drawable.no_activity),
            modifier = Modifier
                .height(140.dp)
                .width(100.dp),
            contentDescription = template.name,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )
    } else {
        val bitmap = BitmapFactory.decodeStream(
            template.thumb().path.invoke().openStream()).asImageBitmap()
        Image(
            bitmap = bitmap,
            contentDescription = template.name,
            modifier = Modifier
                .height(140.dp)
                .width(100.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = true),
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun TemplateTitle(title: String) {
    Text(
        text = title,
        maxLines = 1,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium
    )
}