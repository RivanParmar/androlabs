package com.rivan.androlabs.core.model.data

/**
 * External data layer representation of a fully populated AndroidPlaygrounds project resource
 */
// TODO: Refactor to a better name (maybe) along with other related items
data class ProjectResource(
    val id: String,
    val title: String,
    // TODO: Maybe create another class defining types of project (or lab) levels such as beginner,
    // foundational, etc. or remove this altogether.
    val level: String,
    val description: String,
    val path: String?,
    val type: List<ProjectResourceType>
)