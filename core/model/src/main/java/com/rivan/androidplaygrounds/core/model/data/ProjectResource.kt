package com.rivan.androidplaygrounds.core.model.data

/**
 * External data layer representation of a fully populated AndroidPlaygrounds project resource
 */
// TODO: Refactor to a better name (maybe) along with other related items
data class ProjectResource(
    val id: String,
    val title: String,
    val description: String,
    val path: String?,
    val type: ProjectResourceType
)

val previewProjectResource = listOf(
    ProjectResource(
        id = "1",
        title = "Android App 1",
        description = "First Android app made by me.",
        path = "/downloads/",
        type = ProjectResourceType.Project
    ),
    ProjectResource(
        id = "2",
        title = "Android Playground 1",
        description = "First Android Playground.",
        path = null,
        type = ProjectResourceType.Playground
    )
)
