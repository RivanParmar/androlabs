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
import com.rivan.androlabs.core.model.data.UserLabData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserLabPrefsDataSource @Inject constructor(
    private val userLabPrefs: DataStore<UserLabPrefs>
) {
    val userLabData = userLabPrefs.data
        .map {
            UserLabData(
                recentLabs = it.recentLabIdsMap.keys,
                favouriteLabs = it.favouriteLabIdsMap.keys,
                completedLabs = it.completedLabIdsMap.keys
            )
        }

    suspend fun setRecentLabIds(labIds: Set<String>) {
        try {
            userLabPrefs.updateData {
                it.copy {
                    recentLabIds.clear()
                    recentLabIds.putAll(labIds.associateWith { true })
                }
            }
        } catch (ioException: IOException) {
            Log.e("LabPrefs", "Failed to set recent labs", ioException)
        }
    }

    suspend fun toggleRecentLabId(labId: String, recent: Boolean) {
        try {
            userLabPrefs.updateData {
                it.copy {
                    if (recent) {
                        recentLabIds.put(labId, true)
                    } else {
                        recentLabIds.remove(labId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("LabPrefs", "Failed to toggle recent lab", ioException)
        }
    }

    suspend fun toggleLabFavourite(labId: String, favourite: Boolean) {
        try {
            userLabPrefs.updateData {
                it.copy {
                    if (favourite) {
                        favouriteLabIds.put(labId, favourite)
                    } else {
                        favouriteLabIds.remove(labId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("LabPrefs",
                "Failed to update favourite labs", ioException)
        }
    }

    suspend fun toggleLabCompleted(labId: String, completed: Boolean) {
        try {
            userLabPrefs.updateData {
                it.copy {
                    if (completed) {
                        completedLabIds.put(labId, completed)
                    } else {
                        completedLabIds.remove(labId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("LabPrefs",
                "Failed to update completed labs", ioException)
        }
    }

    suspend fun updateRecentLabs(labId: String, add: Boolean) {
        try {
            userLabPrefs.updateData {
                it.copy {
                    if (add) {
                        recentLabIds.put(labId, add)
                    } else {
                        recentLabIds.remove(labId)
                    }
                }
            }
        } catch (ioException: IOException) {
            Log.e("LabPrefs",
                "Failed to update recent labs", ioException)
        }
    }

    suspend fun getChangeListVersions() = userLabPrefs.data
        .map {
            ChangeListVersions(
                labVersion = it.labChangeListVersion
            )
        }
        .firstOrNull() ?: ChangeListVersions()

    /**
     * Update the [ChangeListVersions] using [update].
     */
    suspend fun updateChangeListVersion(update: ChangeListVersions.() -> ChangeListVersions) {
        try {
            userLabPrefs.updateData { currentPreferences ->
                val updatedChangeListVersions = update(
                    ChangeListVersions(
                        labVersion =
                        currentPreferences.labChangeListVersion
                    )
                )

                currentPreferences.copy {
                    labChangeListVersion =
                        updatedChangeListVersions.labVersion
                }
            }
        } catch (ioException: IOException) {
            Log.e("LabPrefs", "Failed to update changelist", ioException)
        }
    }
}