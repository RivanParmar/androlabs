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

package com.rivan.androlabs.core.domain.model

import com.rivan.androlabs.core.model.data.ProjectResource
import com.rivan.androlabs.core.model.data.ProjectResourceType
import com.rivan.androlabs.core.model.data.UserProjectResourceData
import kotlinx.datetime.Instant

/**
 * A [ProjectResource] with additional user information such as whether the user has saved
 * this project resource and whether they have completed this project (or lab).
 */
data class UserProjectResource internal constructor(
    val id: String,
    val title: String,
    val extraTitle: String,
    val description: String,
    val url: String?,
    val headerImageUrl: String?,
    val lastEdited: Instant?,
    val path: String?,
    val type: ProjectResourceType,
    val isRecent: Boolean,
    val isFavourite: Boolean,
    val isCompleted: Boolean
) {
    constructor(
        projectResource: ProjectResource,
        userProjectResourceData: UserProjectResourceData
    ) : this(
        id = projectResource.id,
        title = projectResource.title,
        extraTitle = projectResource.extraTitle,
        description = projectResource.description,
        url = projectResource.url,
        headerImageUrl = projectResource.headerImageUrl,
        lastEdited = projectResource.lastEdited,
        path = projectResource.path,
        type = projectResource.type,
        isRecent = userProjectResourceData.recentProjectResources.contains(projectResource.id),
        isFavourite = userProjectResourceData
            .favouriteProjectResources.contains(projectResource.id),
        isCompleted = userProjectResourceData.completedProjectResources.contains(projectResource.id)
    )
}

fun List<ProjectResource>.mapToUserProjectResources(
    userProjectResourceData: UserProjectResourceData
): List<UserProjectResource> {
    return map { UserProjectResource(it, userProjectResourceData) }
}