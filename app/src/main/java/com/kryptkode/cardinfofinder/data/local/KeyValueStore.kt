package com.kryptkode.cardinfofinder.data.local

import kotlinx.coroutines.flow.Flow

interface KeyValueStore {
    suspend fun setSeenWalkthrough(value: Boolean)
    val seenWalkthrough: Flow<Boolean>
}
