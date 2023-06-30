package com.rivan.androlabs.core.datastore

import androidx.datastore.core.DataMigration

/**
 * Migrates saved ids from [Int] to [String] types.
 */
object IntToStringIdsMigration : DataMigration<UserLabPrefs> {

    override suspend fun cleanUp() = Unit

    override suspend fun shouldMigrate(currentData: UserLabPrefs): Boolean =
        !currentData.hasDoneIntToStringIdMigration

    override suspend fun migrate(currentData: UserLabPrefs): UserLabPrefs =
        currentData.copy {
            // Migrate recent lab ids
            deprecatedRecentLabIds.clear()
            deprecatedRecentLabIds.addAll(
                currentData.deprecatedIntRecentLabIdsList.map(Int::toString)
            )
            deprecatedIntRecentLabIds.clear()

            // Mark migration as complete
            hasDoneIntToStringIdMigration = true
        }
}