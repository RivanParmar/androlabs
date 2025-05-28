package com.rivan.androlabs.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.rivan.androlabs.core.database.model.LabEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [Lab] and [LabEntity] access.
 */
@Dao
interface LabDao {

    /**
     * Fetches labs that match the query parameters
     */
    @Transaction
    @Query(
        value = """
            SELECT * FROM labs
            WHERE
                CASE WHEN :useFilterLabIds
                    THEN id IN (:filterLabIds)
                    ELSE 1
                END
            ORDER BY last_edited DESC
        """
    )
    fun getLabs(
        useFilterLabIds: Boolean = false,
        filterLabIds: Set<Long> = emptySet(),
    ): Flow<List<LabEntity>>

    /**
     * Inserts [entities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreLabs(
        entities: List<LabEntity>,
    )

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateLabs(entities: List<LabEntity>)

    /**
     * Inserts or updates [labsEntities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertLabs(
        labsEntities: List<LabEntity>
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM labs
            WHERE id in (:ids)
        """,
    )
    suspend fun deleteLabs(ids: List<Long>)
}