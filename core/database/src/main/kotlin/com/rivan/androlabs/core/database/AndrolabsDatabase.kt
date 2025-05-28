package com.rivan.androlabs.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rivan.androlabs.core.database.dao.LabDao
import com.rivan.androlabs.core.database.dao.LabsFtsDao
import com.rivan.androlabs.core.database.dao.RecentSearchQueryDao
import com.rivan.androlabs.core.database.model.LabEntity
import com.rivan.androlabs.core.database.model.LabFtsEntity
import com.rivan.androlabs.core.database.model.RecentSearchQueryEntity
import com.rivan.androlabs.core.database.util.InstantConverter
import com.rivan.androlabs.core.database.util.LabTypeConverter

@Database(
    entities = [
        LabEntity::class,
        LabFtsEntity::class,
        RecentSearchQueryEntity::class,
    ],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
    ],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    LabTypeConverter::class,
)
abstract class AndrolabsDatabase : RoomDatabase() {
    abstract fun labDao(): LabDao
    abstract fun labFtsDao(): LabsFtsDao
    abstract fun recentSearchQueryDao(): RecentSearchQueryDao
}