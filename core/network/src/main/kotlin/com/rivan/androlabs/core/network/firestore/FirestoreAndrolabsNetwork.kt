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

package com.rivan.androlabs.core.network.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.rivan.androlabs.core.network.AndrolabsNetworkDataSource
import com.rivan.androlabs.core.network.model.FirestoreChangeList
import com.rivan.androlabs.core.network.model.FirestoreProjectResource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [FirebaseFirestore] backed [AndrolabsNetworkDataSource]
 */
@Singleton
class FirestoreAndrolabsNetwork @Inject constructor(
    database: FirebaseFirestore
) : AndrolabsNetworkDataSource {

    private val projectResourceFirestoreDb = database.collection("project-resources")

    private val changeListFirestoreDb = database.collection(TODO("Not yet created"))

    override suspend fun getProjectResources(ids: List<String>?): List<FirestoreProjectResource> {
        val projectResources = arrayListOf<FirestoreProjectResource>()
        projectResourceFirestoreDb
            .whereEqualTo("id", ids)
            // TODO: Add field for sorting the data with
            .orderBy("", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val projectResource = document.toObject(FirestoreProjectResource::class.java)
                    projectResources.add(projectResource)
                }
            }
            .addOnFailureListener {
                Log.e("ProjectResourceFirestore", "Could not get project resources!")
            }

        return projectResources
    }

    override suspend fun getProjectResourceChangeList(after: Int?): List<FirestoreChangeList> {
        val changeLists = arrayListOf<FirestoreChangeList>()
        changeListFirestoreDb
            .whereEqualTo("after", after)
            // TODO: Use orderBy if necessary
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val changeList = document.toObject(FirestoreChangeList::class.java)
                    changeLists.add(changeList)
                }
            }
            .addOnFailureListener {
                Log.e("ChangeListFirestore", "Could not get change lists!")
            }

        return changeLists
    }
}