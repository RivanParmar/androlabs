package com.rivan.androlabs.core.domain

import com.rivan.androlabs.core.data.repository.ProjectRepository
import com.rivan.androlabs.core.data.repository.ProjectResourceQuery
import com.rivan.androlabs.core.data.repository.UserProjectResourceDataRepository
import com.rivan.androlabs.core.domain.model.UserProjectResource
import com.rivan.androlabs.core.domain.model.mapToUserProjectResources
import com.rivan.androlabs.core.model.data.ProjectResource
import com.rivan.androlabs.core.model.data.UserProjectResourceData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import javax.inject.Inject

/**
 * A use case responsible for obtaining project resources with their associated favourited state.
 */
class GetUserProjectResourcesUseCase @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val userProjectResourceDataRepository: UserProjectResourceDataRepository
) {
    /**
     * Returns a list of [UserProjectResources] which match the supplied set of project resource
     * ids.
     *
     * @param query Summary of query parameters for project resources.
     */
    operator fun invoke(
        query: ProjectResourceQuery = ProjectResourceQuery()
    ): Flow<List<UserProjectResource>> =
        projectRepository.getProjectResources(
            query = query
        ).mapToUserProjectResources(userProjectResourceDataRepository.userProjectResourceData)
}

private fun Flow<List<ProjectResource>>.mapToUserProjectResources(
    userDataStream: Flow<UserProjectResourceData>,
): Flow<List<UserProjectResource>> =
    filterNot { it.isEmpty() }
        .combine(userDataStream) { projectResources, userProjectResourceData ->
            projectResources.mapToUserProjectResources(userProjectResourceData)
        }