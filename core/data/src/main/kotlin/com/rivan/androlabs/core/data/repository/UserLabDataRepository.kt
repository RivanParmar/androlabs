package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.model.data.UserLabData
import kotlinx.coroutines.flow.Flow

interface UserLabDataRepository {

    /**
     * Stream of [UserLabData].
     */
    val userLabData: Flow<UserLabData>

    /**
     * Sets the user's recently opened labs.
     */
    suspend fun setRecentLabIds(recentLabIds: Set<String>)

    /**
     * Toggles the user's recently added/removed lab.
     */
    suspend fun toggleRecentLabId(labId: String, recent: Boolean)

    /**
     * Updates the favourite status for a lab.
     */
    suspend fun updateLabFavourite(labId: String, favourite: Boolean)

    /**
     * Updates the completed status for a lab.
     */
    suspend fun updateLabCompleted(labId: String, completed: Boolean)
}