package com.rivan.androlabs.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rivan.androlabs.core.database.dao.ProjectResourceDao
import com.rivan.androlabs.core.database.model.ProjectResourceEntity
import com.rivan.androlabs.core.database.util.InstantConverter
import com.rivan.androlabs.core.database.util.ProjectResourceTypeConverter

@Database(
    entities = [ProjectResourceEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    InstantConverter::class,
    ProjectResourceTypeConverter::class
)
abstract class AndrolabsDatabase : RoomDatabase() {
    abstract fun projectResourceDao(): ProjectResourceDao
}