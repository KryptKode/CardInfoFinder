package com.kryptkode.cardinfofinder.data.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface KeyValueStoreModule {

    @Binds
    @Singleton
    fun bindKeyValueStore(keuValueStoreImpl: KeyValueStoreImpl): KeyValueStore
}
