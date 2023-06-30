package com.rivan.androlabs.core.data.repository.fake

import com.rivan.androlabs.core.data.Synchronizer
import com.rivan.androlabs.core.data.model.asEntity
import com.rivan.androlabs.core.data.repository.LabQuery
import com.rivan.androlabs.core.data.repository.LabRepository
import com.rivan.androlabs.core.database.model.LabEntity
import com.rivan.androlabs.core.database.model.asExternalModel
import com.rivan.androlabs.core.model.data.Lab
import com.rivan.androlabs.core.network.AndrolabsDispatcher.IO
import com.rivan.androlabs.core.network.Dispatcher
import com.rivan.androlabs.core.network.fake.FakeAndrolabsNetworkDataSource
import com.rivan.androlabs.core.network.model.FirestoreLab
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Fake implementation of the [LabRepository] that retrieves the labs from a JSON String.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeLabRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: FakeAndrolabsNetworkDataSource
) : LabRepository {

    override fun getLabs(query: LabQuery): Flow<List<Lab>> =
        flow {
            emit(
                dataSource
                    .getLabs()
                    .filter { firestoreLab ->
                        // Filter out any labs which don't match the current query.
                        // If no query parameters (filterLabIds) are specified then the
                        // lab is returned.
                        listOfNotNull(
                            true,
                            query.filterLabIds?.contains(firestoreLab.id)
                        )
                            .all(true::equals)
                    }
                    .map(FirestoreLab::asEntity)
                    .map(LabEntity::asExternalModel)
            )
        }.flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}