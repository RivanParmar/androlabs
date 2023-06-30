package com.rivan.androlabs.core.domain

import com.rivan.androlabs.core.data.repository.LabQuery
import com.rivan.androlabs.core.data.repository.LabRepository
import com.rivan.androlabs.core.data.repository.UserLabDataRepository
import com.rivan.androlabs.core.domain.model.UserLabs
import com.rivan.androlabs.core.domain.model.mapToUserLabs
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.model.data.UserLabData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import javax.inject.Inject

/**
 * A use case responsible for obtaining labs with their associated favourited state.
 */
class GetUserLabsUseCase @Inject constructor(
    private val labRepository: LabRepository,
    private val userLabDataRepository: UserLabDataRepository
) {
    /**
     * Returns a list of [UserLabs] which match the supplied set of lab ids.
     *
     * @param query Summary of query parameters for labs.
     */
    operator fun invoke(
        query: LabQuery = LabQuery()
    ): Flow<List<UserLabs>> =
        labRepository.getLabs(
            query = query
        ).mapToUserLabs(userLabDataRepository.userLabData)
}

private fun Flow<List<Lab>>.mapToUserLabs(
    userDataStream: Flow<UserLabData>,
): Flow<List<UserLabs>> =
    filterNot { it.isEmpty() }
        .combine(userDataStream) { labs, userLabData ->
            labs.mapToUserLabs(userLabData)
        }