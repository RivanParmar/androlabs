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

package com.rivan.androlabs.core.network.model

import com.rivan.androlabs.core.model.data.LabType
import com.rivan.androlabs.core.network.model.util.LabTypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Firestore representation of [Lab].
 */
@Serializable
data class FirestoreLab(
    val id: Long,
    val title: String,
    val extraTitle: String,
    val description: String,
    val url: String?,
    val headerImageUrl: String?,
    val iconPath: String?,
    val lastEdited: Instant?,
    val path: String?,
    @Serializable(LabTypeSerializer::class)
    val type: LabType,
    val vendor: String?,
)