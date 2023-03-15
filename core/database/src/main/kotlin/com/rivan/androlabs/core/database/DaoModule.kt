package com.rivan.androlabs.core.database

import com.rivan.androlabs.core.database.dao.ProjectResourceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun providesProjectResourceDao(
        database: AndroLabsDatabase
    ): ProjectResourceDao = database.projectResourceDao()
}