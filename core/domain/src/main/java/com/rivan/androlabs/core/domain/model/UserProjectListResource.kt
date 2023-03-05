package com.rivan.androlabs.core.domain.model

import com.rivan.androlabs.core.model.data.ProjectListResource
import com.rivan.androlabs.core.model.data.ProjectListResourceType
import com.rivan.androlabs.core.model.data.UserProjectListResourceData
import kotlinx.datetime.Instant

/**
 * A [ProjectListResource] with additional user information such as whether the user has saved
 * this project resource and whether they have completed this project (or lab).
 */
data class UserProjectListResource internal constructor(
    val id: String,
    val title: String,
    val extraTitle: String,
    val description: String,
    val headerImageUrl: String?,
    val lastEdited: Instant?,
    val path: String?,
    val type: List<ProjectListResourceType>,
    val isFavourite: Boolean,
    val isCompleted: Boolean
) {
    constructor(
        projectListResource: ProjectListResource,
        userProjectListResourceData: UserProjectListResourceData
    ) : this(
        id = projectListResource.id,
        title = projectListResource.title,
        extraTitle = projectListResource.extraTitle,
        description = projectListResource.description,
        headerImageUrl = projectListResource.headerImageUrl,
        lastEdited = projectListResource.lastEdited,
        path = projectListResource.path,
        type = projectListResource.type,
        isFavourite = userProjectListResourceData
            .favouriteProjectResources.contains(projectListResource.id),
        isCompleted = userProjectListResourceData.completedLabs.contains(projectListResource.id)
    )
}

fun List<ProjectListResource>.mapToUserProjectResources(
    userProjectListResourceData: UserProjectListResourceData
): List<UserProjectListResource> {
    return map { UserProjectListResource(it, userProjectListResourceData) }
}