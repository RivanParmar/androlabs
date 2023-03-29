package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.model.data.UserProjectResourceData
import kotlinx.coroutines.flow.Flow

interface UserProjectResourceDataRepository {

    /**
     * Stream of [UserProjectResourceData].
     */
    val userProjectResourceData: Flow<UserProjectResourceData>

    /**
     * Sets the user's recently opened project resources.
     */
    suspend fun setRecentProjectResourceIds(recentProjectResourceIds: Set<String>)

    /**
     * Toggles the user's recently added/removed project resource.
     */
    suspend fun toggleRecentProjectResourceId(projectResourceId: String, recent: Boolean)

    /**
     * Updates the favourite status for a project resource.
     */
    suspend fun updateProjectResourceFavourite(projectResourceId: String, favourite: Boolean)

    /**
     * Updates the completed status for a lab. Can also be used for projects.
     */
    suspend fun updateProjectResourceCompleted(projectResourceId: String, completed: Boolean)
}