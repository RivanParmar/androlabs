package com.rivan.androlabs.core.domain.model

import com.rivan.androlabs.core.model.data.ProjectResource
import com.rivan.androlabs.core.model.data.ProjectResourceType
import com.rivan.androlabs.core.model.data.UserData

/**
 * A [ProjectResource] with additional user information such as whether the user has saved
 * this project resource and whether they have completed this project (or lab).
 */
data class UserProjectResource internal constructor(
    val id: String,
    val title: String,
    val level: String,
    val description: String,
    val path: String?,
    val type: List<ProjectResourceType>,
    val isFavourite: Boolean,
    val isCompleted: Boolean
) {
    constructor(projectResource: ProjectResource, userData: UserData) : this(
        id = projectResource.id,
        title = projectResource.title,
        level = projectResource.level,
        description = projectResource.description,
        path = projectResource.path,
        type = projectResource.type,
        isFavourite = userData.favouriteProjectResources.contains(projectResource.id),
        isCompleted = userData.completedProjectResources.contains(projectResource.id)
    )
}