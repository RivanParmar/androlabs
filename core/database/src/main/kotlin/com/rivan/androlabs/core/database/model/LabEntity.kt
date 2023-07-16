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

package com.rivan.androlabs.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.model.data.LabType
import kotlinx.datetime.Instant

/**
 * Defines an Androlabs lab.
 */
@Entity(
    tableName = "labs"
)
data class LabEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    @ColumnInfo(name = "extra_title")
    val extraTitle: String,
    val description: String,
    val url: String?,
    @ColumnInfo(name = "header_image_url")
    val headerImageUrl: String?,
    @ColumnInfo(name = "icon_path")
    val iconPath: String?,
    @ColumnInfo(name = "last_edited")
    val lastEdited: Instant?,
    val path: String?,
    val type: LabType,
    val vendor: String?
)

fun LabEntity.asExternalModel() = Lab(
    id = id,
    title = title,
    extraTitle = extraTitle,
    description = description,
    url = url,
    headerImageUrl = headerImageUrl,
    iconPath = iconPath,
    lastEdited = lastEdited,
    path = path,
    type = type,
    vendor = vendor
)