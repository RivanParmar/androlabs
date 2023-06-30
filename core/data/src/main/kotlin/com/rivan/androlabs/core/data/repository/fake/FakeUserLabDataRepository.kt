package com.rivan.androlabs.core.data.repository.fake

import com.rivan.androlabs.core.data.repository.UserLabDataRepository
import com.rivan.androlabs.core.datastore.UserLabPrefsDataSource
import com.rivan.androlabs.core.model.data.UserLabData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Fake implementation of the [UserLabDataRepository] that return hardcoded user lab data.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeUserLabDataRepository @Inject constructor(
    private val userLabPrefsDataSource: UserLabPrefsDataSource
) : UserLabDataRepository {

    override val userLabData: Flow<UserLabData> =
        userLabPrefsDataSource.userLabData

    override suspend fun setRecentLabIds(
        recentLabIds: Set<String>
    ) = userLabPrefsDataSource.setRecentLabIds(recentLabIds)

    override suspend fun toggleRecentLabId(
        labId: String, recent: Boolean
    ) = userLabPrefsDataSource.toggleRecentLabId(labId, recent)

    override suspend fun updateLabFavourite(
        labId: String, favourite: Boolean
    ) {
        userLabPrefsDataSource.toggleLabFavourite(
            labId, favourite
        )
    }

    override suspend fun updateLabCompleted(
        labId: String, completed: Boolean
    ) {
        userLabPrefsDataSource.toggleLabCompleted(
            labId, completed
        )
    }
}