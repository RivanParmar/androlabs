package com.rivan.androlabs.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rivan.androlabs.core.database.dao.LabDao
import com.rivan.androlabs.core.database.model.LabEntity
import com.rivan.androlabs.core.database.util.InstantConverter
import com.rivan.androlabs.core.database.util.LabTypeConverter

@Database(
    entities = [LabEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    InstantConverter::class,
    LabTypeConverter::class
)
abstract class AndrolabsDatabase : RoomDatabase() {
    abstract fun labDao(): LabDao
}