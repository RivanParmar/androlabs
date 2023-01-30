package com.rivan.androidplaygrounds.core.model.data

/**
 * External data layer representation of a fully populated AndroidPlaygrounds project resource
 */
// TODO: Refactor to a better name (maybe) along with other related items
data class ProjectResource(
    val id: String,
    val title: String,
    val description: String,
    val projectPath: String?,
    val type: ProjectResourceType
)
