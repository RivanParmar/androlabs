package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.data.Synchronizer
import com.rivan.androlabs.core.data.changeListSync
import com.rivan.androlabs.core.data.model.asEntity
import com.rivan.androlabs.core.database.dao.LabDao
import com.rivan.androlabs.core.database.model.LabEntity
import com.rivan.androlabs.core.database.model.asExternalModel
import com.rivan.androlabs.core.datastore.ChangeListVersions
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.network.AndrolabsNetworkDataSource
import com.rivan.androlabs.core.network.model.FirestoreLab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Heuristic value to optimize for serialization and deserialization cost on client and server
// for each news resource batch.
private const val SYNC_BATCH_SIZE = 40

/**
 * Disk storage backed implementation of [LabRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstLabRepository @Inject constructor(
    private val labDao: LabDao,
    private val network: AndrolabsNetworkDataSource
) : LabRepository {

    override fun getLabs(
        query: LabQuery
    ): Flow<List<Lab>> = labDao.getLabs(
        useFilterLabIds = query.filterLabIds != null,
        filterLabIds = query.filterLabIds ?: emptySet()
    )
        .map { it.map(LabEntity::asExternalModel) }

    override suspend fun syncWith(synchronizer: Synchronizer) =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::labVersion,
            changeListFetcher = { currentVersion ->
                network.getLabChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(labVersion = latestVersion)
            },
            modelDeleter = labDao::deleteLabs,
            modelUpdater = { changeIds ->
                changeIds.chunked(SYNC_BATCH_SIZE).forEach { chunkedIds ->
                    val firestoreProjectResource = network.getLabs(ids = chunkedIds)

                    labDao.upsertLabs(
                        labsEntities = firestoreProjectResource.map(
                            FirestoreLab::asEntity
                        )
                    )
                }
            }
        )

}