package com.rivan.androlabs.core.domain

import com.rivan.androlabs.core.data.repository.LabQuery
import com.rivan.androlabs.core.data.repository.LabRepository
import com.rivan.androlabs.core.model.data.Lab
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case responsible for obtaining labs with their associated state.
 */
class GetLabsUseCase @Inject constructor(
    private val labRepository: LabRepository,
//    private val userLabDataRepository: UserLabDataRepository
) {
    /**
     * Returns a list of [Lab] which match the supplied set of lab ids.
     *
     * @param query Summary of query parameters for labs.
     */
    operator fun invoke(
        query: LabQuery = LabQuery(),
    ): Flow<List<Lab>> = labRepository.getLabs(query = query)
}

/*private fun Flow<List<Lab>>.mapToUserLabs(
    userDataStream: Flow<UserLabData>,
): Flow<List<UserLabs>> =
    filterNot { it.isEmpty() }
        .combine(userDataStream) { labs, userLabData ->
            labs.mapToUserLabs(userLabData)
        }*/