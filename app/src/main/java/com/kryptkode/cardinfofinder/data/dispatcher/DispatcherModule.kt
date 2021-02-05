package com.kryptkode.cardinfofinder.data.dispatcher

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DispatcherModule {

    @Binds
    @Singleton
    fun bindAppDispatchers(appDispatchersImpl: AppDispatchersImpl): AppDispatchers
}
