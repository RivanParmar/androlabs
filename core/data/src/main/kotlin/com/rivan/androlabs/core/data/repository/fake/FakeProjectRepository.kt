package com.rivan.androlabs.core.data.repository.fake

import com.rivan.androlabs.core.data.Synchronizer
import com.rivan.androlabs.core.data.model.asEntity
import com.rivan.androlabs.core.data.repository.ProjectRepository
import com.rivan.androlabs.core.data.repository.ProjectResourceQuery
import com.rivan.androlabs.core.database.model.ProjectResourceEntity
import com.rivan.androlabs.core.database.model.asExternalModel
import com.rivan.androlabs.core.model.data.ProjectResource
import com.rivan.androlabs.core.network.AndroLabsDispatcher.IO
import com.rivan.androlabs.core.network.Dispatcher
import com.rivan.androlabs.core.network.fake.FakeAndrolabsNetworkDataSource
import com.rivan.androlabs.core.network.model.FirestoreProjectResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Fake implementation of the [ProjectRepository] that retrieves the project resources from a JSON
 * String.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeProjectRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: FakeAndrolabsNetworkDataSource
) : ProjectRepository {

    override fun getProjectResources(query: ProjectResourceQuery): Flow<List<ProjectResource>> =
        flow {
            emit(
                dataSource
                    .getProjectResources()
                    .filter { firestoreProjectResource ->
                        // Filter out any project resources which don't match the current query.
                        // If no query parameters (filterProjectIds) are specified then the
                        // project resource is returned.
                        listOfNotNull(
                            true,
                            query.filterProjectIds?.contains(firestoreProjectResource.id)
                        )
                            .all(true::equals)
                    }
                    .map(FirestoreProjectResource::asEntity)
                    .map(ProjectResourceEntity::asExternalModel)
            )
        }.flowOn(ioDispatcher)

    override suspend fun syncWith(synchronizer: Synchronizer) = true
}