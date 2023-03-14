package com.rivan.androlabs.core.model.data

import kotlinx.datetime.Instant

/**
 * External data layer representation of a fully populated Andro Labs project list resource
 */
// TODO: Refactor to a better name (maybe) along with other related items
data class ProjectResource(
    val id: String,
    val title: String,
    val extraTitle: String,
    val description: String,
    val url: String?,
    val headerImageUrl: String?,
    val lastEdited: Instant?,
    val path: String?,
    val type: ProjectResourceType
)