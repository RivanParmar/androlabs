package com.rivan.androlabs.core.datastore

import androidx.datastore.core.DataMigration

/**
 * Migrates saved ids from [Int] to [String] types.
 */
object IntToStringIdsMigration : DataMigration<UserProjectResourcePrefs> {

    override suspend fun cleanUp() = Unit

    override suspend fun shouldMigrate(currentData: UserProjectResourcePrefs): Boolean =
        !currentData.hasDoneIntToStringIdMigration

    override suspend fun migrate(currentData: UserProjectResourcePrefs): UserProjectResourcePrefs =
        currentData.copy {
            // Migrate recent project resource ids
            deprecatedRecentProjectResourceIds.clear()
            deprecatedRecentProjectResourceIds.addAll(
                currentData.deprecatedIntRecentProjectResourceIdsList.map(Int::toString)
            )
            deprecatedIntRecentProjectResourceIds.clear()

            // Mark migration as complete
            hasDoneIntToStringIdMigration = true
        }
}