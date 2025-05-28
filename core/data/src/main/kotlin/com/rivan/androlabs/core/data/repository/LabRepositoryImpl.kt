package com.rivan.androlabs.core.data.repository

import com.rivan.androlabs.core.database.dao.LabDao
import com.rivan.androlabs.core.database.model.LabEntity
import com.rivan.androlabs.core.database.model.asExternalModel
import com.rivan.androlabs.core.model.data.Lab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LabRepositoryImpl @Inject constructor(
    private val labDao: LabDao,
) : LabRepository {

    override fun getLabs(
        query: LabQuery,
    ): Flow<List<Lab>> = labDao.getLabs(
        useFilterLabIds = query.filterLabIds != null,
        filterLabIds = query.filterLabIds ?: emptySet(),
    )
        .map { it.map(LabEntity::asExternalModel) }


    override suspend fun insertOrIgnoreLabs(labs: List<Lab>) {
        labDao.insertOrIgnoreLabs(
            labs.map(Lab::asEntity),
        )
    }

    override suspend fun updateLabs(labs: List<Lab>) {
        labDao.updateLabs(
            labs.map(Lab::asEntity),
        )
    }

    override suspend fun upsertLabs(labs: List<Lab>) {
        labDao.upsertLabs(
            labs.map(Lab::asEntity),
        )
    }

    override suspend fun deleteLabs(ids: List<Long>) {
        labDao.deleteLabs(ids)
    }
}

fun Lab.asEntity() = LabEntity(
    id = id,
    title = title,
    extraTitle = extraTitle,
    description = description,
    url = url,
    headerImageUrl = headerImageUrl,
    iconPath = iconPath,
    lastEdited = lastEdited,
    path = path,
    type = type,
    vendor = vendor,
)