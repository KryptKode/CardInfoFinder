package com.kryptkode.cardinfofinder.data.usecase

import com.kryptkode.cardinfofinder.data.dispatcher.AppDispatchers
import com.kryptkode.cardinfofinder.data.local.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SeenWalkThroughUseCase @Inject constructor(
    private val keyValueStore: KeyValueStore,
    private val dispatchers: AppDispatchers
) {

    suspend fun setSeenWalkthrough(value: Boolean) {
        return withContext(dispatchers.io) {
            keyValueStore.setSeenWalkthrough(value)
        }
    }

    fun seenWalkthrough(): Flow<Boolean> {
        return keyValueStore.seenWalkthrough.flowOn(dispatchers.io)
    }
}
