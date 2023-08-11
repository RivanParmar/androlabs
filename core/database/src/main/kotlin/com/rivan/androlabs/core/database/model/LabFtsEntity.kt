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
import androidx.room.Fts4

/**
 * Fts entity for the labs. See https://developer.android.com/reference/androidx/room/Fts4.
 */
@Entity(tableName = "labsFts")
@Fts4
data class LabFtsEntity(
    @ColumnInfo(name = "labId")
    val labId: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "extraTitle")
    val extraTitle: String,

    @ColumnInfo(name = "description")
    val description: String,
)