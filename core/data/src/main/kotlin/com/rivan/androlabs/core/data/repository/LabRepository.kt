package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.model.data.Lab
import kotlinx.coroutines.flow.Flow

/**
 * Encapsulation class for query parameters for [Lab].
 */
data class LabQuery(
    /**
     * Project IDs to filter for. Null means any project ID will match.
     */
    val filterLabIds: Set<Long>? = null,
)

/**
 * Data layer implementation for [Lab].
 */
interface LabRepository {

    /**
     * Returns available labs that match the specified [query].
     */
    fun getLabs(
        query: LabQuery = LabQuery(
            filterLabIds = null,
        ),
    ): Flow<List<Lab>>

    suspend fun insertOrIgnoreLabs(labs: List<Lab>)

    suspend fun updateLabs(labs: List<Lab>)

    suspend fun upsertLabs(labs: List<Lab>)

    suspend fun deleteLabs(ids: List<Long>)
}