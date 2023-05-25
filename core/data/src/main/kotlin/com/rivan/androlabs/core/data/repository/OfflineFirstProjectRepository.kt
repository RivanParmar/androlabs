package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.data.Synchronizer
import com.rivan.androlabs.core.data.changeListSync
import com.rivan.androlabs.core.data.model.asEntity
import com.rivan.androlabs.core.database.dao.ProjectResourceDao
import com.rivan.androlabs.core.database.model.ProjectResourceEntity
import com.rivan.androlabs.core.database.model.asExternalModel
import com.rivan.androlabs.core.datastore.ChangeListVersions
import com.rivan.androlabs.core.model.data.ProjectResource
import com.rivan.androlabs.core.network.AndrolabsNetworkDataSource
import com.rivan.androlabs.core.network.model.FirestoreProjectResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Heuristic value to optimize for serialization and deserialization cost on client and server
// for each news resource batch.
private const val SYNC_BATCH_SIZE = 40

/**
 * Disk storage backed implementation of [ProjectRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstProjectRepository @Inject constructor(
    private val projectResourceDao: ProjectResourceDao,
    private val network: AndrolabsNetworkDataSource
) : ProjectRepository {

    override fun getProjectResources(
        query: ProjectResourceQuery
    ): Flow<List<ProjectResource>> = projectResourceDao.getProjectResources(
        useFilterProjectIds = query.filterProjectIds != null,
        filterProjectIds = query.filterProjectIds ?: emptySet()
    )
        .map { it.map(ProjectResourceEntity::asExternalModel) }

    override suspend fun syncWith(synchronizer: Synchronizer) =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::projectResourceVersion,
            changeListFetcher = { currentVersion ->
                network.getProjectResourceChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(projectResourceVersion = latestVersion)
            },
            modelDeleter = projectResourceDao::deleteProjectResources,
            modelUpdater = { changeIds ->
                changeIds.chunked(SYNC_BATCH_SIZE).forEach { chunkedIds ->
                    val firestoreProjectResource = network.getProjectResources(ids = chunkedIds)

                    projectResourceDao.upsertProjectResources(
                        projectResourceEntities = firestoreProjectResource.map(
                            FirestoreProjectResource::asEntity
                        )
                    )
                }
            }
        )

}