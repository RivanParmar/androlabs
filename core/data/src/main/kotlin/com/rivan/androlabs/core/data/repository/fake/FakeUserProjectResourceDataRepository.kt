package com.rivan.androlabs.core.data.repository.fake

import com.rivan.androlabs.core.data.repository.UserProjectResourceDataRepository
import com.rivan.androlabs.core.datastore.UserProjectResourcePrefsDataSource
import com.rivan.androlabs.core.model.data.UserProjectResourceData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Fake implementation of the [UserProjectResourceDataRepository] that return hardcoded user
 * project resource data.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeUserProjectResourceDataRepository @Inject constructor(
    private val userProjectResourcePrefsDataSource: UserProjectResourcePrefsDataSource
) : UserProjectResourceDataRepository {

    override val userProjectResourceData: Flow<UserProjectResourceData> =
        userProjectResourcePrefsDataSource.userProjectResourceData

    override suspend fun setRecentProjectResourceIds(
        recentProjectResourceIds: Set<String>
    ) = userProjectResourcePrefsDataSource.setRecentProjectResourceIds(recentProjectResourceIds)

    override suspend fun toggleRecentProjectResourceId(
        projectResourceId: String, recent: Boolean
    ) = userProjectResourcePrefsDataSource.toggleRecentProjectResourceId(projectResourceId, recent)

    override suspend fun updateProjectResourceFavourite(
        projectResourceId: String, favourite: Boolean
    ) {
        userProjectResourcePrefsDataSource.toggleProjectResourceFavourite(
            projectResourceId, favourite
        )
    }

    override suspend fun updateProjectResourceCompleted(
        projectResourceId: String, completed: Boolean
    ) {
        userProjectResourcePrefsDataSource.toggleProjectResourceCompleted(
            projectResourceId, completed
        )
    }
}