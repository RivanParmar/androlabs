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

package com.rivan.androlabs.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.rivan.androlabs.core.model.data.UserProjectResourceData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserProjectResourcePrefsDataSource @Inject constructor(
    private val userProjectResourcePrefs: DataStore<UserProjectResourcePrefs>
) {
    val userProjectResourceData = userProjectResourcePrefs.data
        .map {
            UserProjectResourceData(
                recentProjectResources = it.recentProjectResourceIdsMap.keys,
                favouriteProjectResources = it.favouriteProjectResourceIdsMap.keys,
                completedProjectResources = it.completedProjectResourceIdsMap.keys
            )
        }

    suspend fun setRecentProjectResourceIds(projectResourceIds: Set<String>) {
        try {
            userProjectResourcePrefs.updateData {
                it.copy {
                    recentProjectResourceIds.clear()
                    recentProjectResourceIds.putAll(projectResourceIds.associateWith { true })
                }
            }
        } catch (ioException: IOException) {
            Log.e("ProjectResourcePrefs", "Failed to set recent project resources", ioException)
        }
    }

    suspend fun toggleRecentProjectResourceId(projectResourceId: String, recent: Boolean) {
        try {
            userProjectResourcePrefs.updateData {
                it.copy {
                    if (recent) {
                        recentProjectResourceIds.put(projectResourceId, true)
                    } else {
                        recentProjectResourceIds.remove(projectResourceId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("ProjectResourcePrefs", "Failed to toggle recent project resource", ioException)
        }
    }

    suspend fun toggleProjectResourceFavourite(projectResourceId: String, favourite: Boolean) {
        try {
            userProjectResourcePrefs.updateData {
                it.copy {
                    if (favourite) {
                        favouriteProjectResourceIds.put(projectResourceId, favourite)
                    } else {
                        favouriteProjectResourceIds.remove(projectResourceId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("ProjectResourcePrefs",
                "Failed to update favourite project resources", ioException)
        }
    }

    suspend fun toggleProjectResourceCompleted(projectResourceId: String, completed: Boolean) {
        try {
            userProjectResourcePrefs.updateData {
                it.copy {
                    if (completed) {
                        completedProjectResourceIds.put(projectResourceId, completed)
                    } else {
                        completedProjectResourceIds.remove(projectResourceId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("ProjectResourcePrefs",
                "Failed to update completed project resources", ioException)
        }
    }

    suspend fun updateRecentProjectResources(projectResourceId: String, add: Boolean) {
        try {
            userProjectResourcePrefs.updateData {
                it.copy {
                    if (add) {
                        recentProjectResourceIds.put(projectResourceId, add)
                    } else {
                        recentProjectResourceIds.remove(projectResourceId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("ProjectResourcePrefs",
                "Failed to update recent project resources", ioException)
        }
    }

    suspend fun getChangeListVersions() = userProjectResourcePrefs.data
        .map {
            ChangeListVersions(
                projectResourceVersion = it.projectResourceChangeListVersion
            )
        }
        .firstOrNull() ?: ChangeListVersions()

    /**
     * Update the [ChangeListVersions] using [update].
     */
    suspend fun updateChangeListVersion(update: ChangeListVersions.() -> ChangeListVersions) {
        try {
            userProjectResourcePrefs.updateData { currentPreferences ->
                val updatedChangeListVersions = update(
                    ChangeListVersions(
                        projectResourceVersion =
                        currentPreferences.projectResourceChangeListVersion
                    )
                )

                currentPreferences.copy {
                    projectResourceChangeListVersion =
                        updatedChangeListVersions.projectResourceVersion
                }
            }
        } catch (ioException: IOException) {
            Log.e("ProjectResourcePrefs", "Failed to update changelist", ioException)
        }
    }
}