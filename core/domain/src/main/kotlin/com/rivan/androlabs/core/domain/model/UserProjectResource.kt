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
    val headerImageUrl: String?,
    val lastEdited: Instant?,
    val path: String?,
    val type: ProjectResourceType,
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
        headerImageUrl = projectResource.headerImageUrl,
        lastEdited = projectResource.lastEdited,
        path = projectResource.path,
        type = projectResource.type,
        isFavourite = userProjectResourceData
            .favouriteProjectResources.contains(projectResource.id),
        isCompleted = userProjectResourceData.completedLabs.contains(projectResource.id)
    )
}

fun List<ProjectResource>.mapToUserProjectResources(
    userProjectResourceData: UserProjectResourceData
): List<UserProjectResource> {
    return map { UserProjectResource(it, userProjectResourceData) }
}