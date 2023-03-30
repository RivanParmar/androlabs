/*
 * Copyright 2023 Rivan Parmar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rivan.androlabs.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.rivan.androlabs.core.data.Synchronizer
import com.rivan.androlabs.core.data.repository.ProjectRepository
import com.rivan.androlabs.core.datastore.ChangeListVersions
import com.rivan.androlabs.core.datastore.UserProjectResourcePrefsDataSource
import com.rivan.androlabs.core.network.AndroLabsDispatcher.IO
import com.rivan.androlabs.core.network.Dispatcher
import com.rivan.androlabs.sync.initializers.SyncConstraints
import com.rivan.androlabs.sync.initializers.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*

/**
 * Syncs the data layer by delegating to the appropriate repository instances with sync
 * functionality.
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val userProjectResourcePrefs: UserProjectResourcePrefsDataSource,
    private val projectRepository: ProjectRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams), Synchronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        // First sync the repository
        val syncedSuccessfully =
            withContext(ioDispatcher) { projectRepository.sync() }

        if (syncedSuccessfully) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    override suspend fun getChangeListVersions(): ChangeListVersions =
        userProjectResourcePrefs.getChangeListVersions()

    override suspend fun updateChangeListVersions(
        update: ChangeListVersions.() -> ChangeListVersions
    ) = userProjectResourcePrefs.updateChangeListVersion(update)

    companion object {
        /**
         * Expedited one time work to sync data on app startup.
         */
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}