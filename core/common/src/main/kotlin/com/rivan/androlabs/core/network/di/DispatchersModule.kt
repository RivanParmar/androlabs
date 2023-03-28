package com.rivan.androlabs.core.network.di

import com.rivan.androlabs.core.network.AndroLabsDispatcher.IO
import com.rivan.androlabs.core.network.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(IO)
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}