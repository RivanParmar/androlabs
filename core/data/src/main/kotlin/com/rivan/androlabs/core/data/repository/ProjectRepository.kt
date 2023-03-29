package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.data.Syncable
import com.rivan.androlabs.core.model.data.ProjectResource
import kotlinx.coroutines.flow.Flow

/**
 * Encapsulation class for query parameters for [ProjectResource].
 */
data class ProjectResourceQuery(
    /**
     * Project IDs to filter for. Null means any project ID will match.
     */
    val filterProjectIds: Set<String>? = null
)

/**
 * Data layer implementation for [ProjectResource].
 */
interface ProjectRepository : Syncable {

    /**
     * Returns available project resources that match the specified [query].
     */
    fun getProjectResources(
        query: ProjectResourceQuery = ProjectResourceQuery(
            filterProjectIds = null
        )
    ): Flow<List<ProjectResource>>
}