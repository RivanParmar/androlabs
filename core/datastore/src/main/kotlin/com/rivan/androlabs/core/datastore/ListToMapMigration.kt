package com.rivan.androlabs.core.datastore

import androidx.datastore.core.DataMigration

object ListToMapMigration : DataMigration<UserLabPrefs> {

    override suspend fun cleanUp() = Unit

    override suspend fun migrate(currentData: UserLabPrefs): UserLabPrefs =
        currentData.copy {
            // Migrate favourites
            favouriteLabIds.clear()
            favouriteLabIds.putAll(
                currentData.deprecatedFavouriteLabIdsList.associateWith { true }
            )
            deprecatedFavouriteLabIds.clear()

            // Migrate completed
            completedLabIds.clear()
            completedLabIds.putAll(
                currentData.deprecatedFavouriteLabIdsList.associateWith { true }
            )
            deprecatedCompletedLabIds.clear()

            // Mark migration as complete
            hasDoneListToMapMigration = true
        }

    override suspend fun shouldMigrate(currentData: UserLabPrefs): Boolean =
        !currentData.hasDoneListToMapMigration
}