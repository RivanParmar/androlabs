package com.rivan.androlabs.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesProjectResourceDatabase(
        @ApplicationContext context: Context
    ): AndroLabsDatabase = Room.databaseBuilder(
        context,
        AndroLabsDatabase::class.java,
        "androlabs-database"
    ).build()
}