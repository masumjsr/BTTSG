package com.mssoftinc.bttsg.utils

import com.mssoftinc.bttsg.datastore.Dispatcher
import com.mssoftinc.bttsg.datastore.Dispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(Dispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO
}