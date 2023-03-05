package com.rivan.androlabs.core.model.data

/**
 * Class summarizing user's project resource data.
 */
data class UserProjectListResourceData(
    val favouriteProjectResources: Set<String>,
    val completedLabs: Set<String>
)