package com.rivan.androlabs.core.database.dao

import androidx.room.*
import com.rivan.androlabs.core.database.model.ProjectResourceEntity

/**
 * DAO for [ProjectResource] and [ProjectResourceEntity] access.
 */
@Dao
interface ProjectResourceDao {

    // TODO: Add function to retrieve values from db

    /**
     * Inserts [entities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreProjectResources(
        entities: List<ProjectResourceEntity>
    ): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateProjectResources(entities: List<ProjectResourceEntity>)

    /**
     * Inserts or updates [projectResourceEntities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertProjectResources(
        projectResourceEntities: List<ProjectResourceEntity>
    )

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM project_resources
            WHERE id in (:ids)
        """
    )
    suspend fun deleteProjectResources(ids: List<String>)
}