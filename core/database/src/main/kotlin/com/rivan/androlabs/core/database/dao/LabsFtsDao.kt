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

package com.rivan.androlabs.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rivan.androlabs.core.database.model.LabFtsEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for [LabFtsEntity] access.
 */
@Dao
interface LabsFtsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(labs: List<LabFtsEntity>)

    @Query("SELECT labId FROM labsFts WHERE labsFts MATCH :query")
    fun searchAllLabs(query: String): Flow<List<String>>

    @Query("SELECT count(*) FROM labsFts")
    fun getCount(): Flow<Int>
}