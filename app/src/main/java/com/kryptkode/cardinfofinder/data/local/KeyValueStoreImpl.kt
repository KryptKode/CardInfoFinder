package com.kryptkode.cardinfofinder.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

const val DATA_STORE_NAME = "CardFinder"

class KeyValueStoreImpl @Inject constructor(@ApplicationContext context: Context) : KeyValueStore {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = DATA_STORE_NAME)

    override suspend fun setSeenWalkthrough(value: Boolean) {
        dataStore.edit {
            it[SEEN_WALKTHROUGH_KEY] = value
        }
    }

    override val seenWalkthrough: Flow<Boolean>
        get() =
            dataStore.data
                .catch(emptyPreferenceOnErrorAction)
                .map { it[SEEN_WALKTHROUGH_KEY] ?: false }

    companion object {
        private val SEEN_WALKTHROUGH_KEY = booleanPreferencesKey("seen_walkthrough")

        private val emptyPreferenceOnErrorAction: suspend FlowCollector<Preferences>.(cause: Throwable) -> Unit =
            { exception ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
    }
}
