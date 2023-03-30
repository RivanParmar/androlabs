package com.rivan.androlabs.core.datastore

import androidx.datastore.core.DataMigration

object ListToMapMigration : DataMigration<UserProjectResourcePrefs> {

    override suspend fun cleanUp() = Unit

    override suspend fun migrate(currentData: UserProjectResourcePrefs): UserProjectResourcePrefs =
        currentData.copy {
            // Migrate favourites
            favouriteProjectResourceIds.clear()
            favouriteProjectResourceIds.putAll(
                currentData.deprecatedFavouriteProjectResourceIdsList.associateWith { true }
            )
            deprecatedFavouriteProjectResourceIds.clear()

            // Migrate completed
            completedProjectResourceIds.clear()
            completedProjectResourceIds.putAll(
                currentData.deprecatedFavouriteProjectResourceIdsList.associateWith { true }
            )
            deprecatedCompletedProjectResourceIds.clear()

            // Mark migration as complete
            hasDoneListToMapMigration = true
        }

    override suspend fun shouldMigrate(currentData: UserProjectResourcePrefs): Boolean =
        !currentData.hasDoneListToMapMigration
}