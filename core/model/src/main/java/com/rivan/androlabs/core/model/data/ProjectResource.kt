package com.rivan.androlabs.core.model.data

/**
 * External data layer representation of a fully populated AndroidPlaygrounds project resource
 */
// TODO: Refactor to a better name (maybe) along with other related items
data class ProjectResource(
    val id: String,
    val title: String,
    val description: String,
    val path: String?,
    val type: List<ProjectResourceType>
)

val previewProjectResource = listOf(
    ProjectResource(
        id = "1",
        title = "Android App 1",
        description = "First Android app made by me.",
        path = "/downloads/",
        type = listOf(ProjectResourceType.Project)
    ),
    ProjectResource(
        id = "2",
        title = "Android Lab 1",
        description = "First Android Lab.",
        path = null,
        type = listOf(ProjectResourceType.Lab)
    )
)
