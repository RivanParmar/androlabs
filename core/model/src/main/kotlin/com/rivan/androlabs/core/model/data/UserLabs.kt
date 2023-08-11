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

package com.rivan.androlabs.core.model.data

import kotlinx.datetime.Instant

/**
 * A [Lab] with additional user information such as whether the user has saved
 * this lab and whether they have completed this lab.
 */
data class UserLabs internal constructor(
    val id: String,
    val title: String,
    val extraTitle: String,
    val description: String,
    val url: String?,
    val headerImageUrl: String?,
    val iconPath: String?,
    val lastEdited: Instant?,
    val path: String?,
    val type: LabType,
    val vendor: String?,
    val isRecent: Boolean,
    val isFavourite: Boolean,
    val isCompleted: Boolean
) {
    constructor(
        lab: Lab,
        userLabData: UserLabData
    ) : this(
        id = lab.id,
        title = lab.title,
        extraTitle = lab.extraTitle,
        description = lab.description,
        url = lab.url,
        headerImageUrl = lab.headerImageUrl,
        iconPath = lab.iconPath,
        lastEdited = lab.lastEdited,
        path = lab.path,
        type = lab.type,
        vendor = lab.vendor,
        isRecent = userLabData.recentLabs.contains(lab.id),
        isFavourite = userLabData
            .favouriteLabs.contains(lab.id),
        isCompleted = userLabData.completedLabs.contains(lab.id)
    )
}

fun List<Lab>.mapToUserLabs(
    userLabData: UserLabData
): List<UserLabs> {
    return map { UserLabs(it, userLabData) }
}