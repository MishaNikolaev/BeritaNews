package com.nmichail.beritanews.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("berita_preferences")

class PreferenceManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val KEY_IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        private val KEY_SELECTED_COUNTRY = stringPreferencesKey("selected_country")
    }

    val isFirstLaunch: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[KEY_IS_FIRST_LAUNCH] ?: true
    }

    val selectedCountry: Flow<String?> = dataStore.data.map { prefs ->
        prefs[KEY_SELECTED_COUNTRY]
    }

    suspend fun setFirstLaunch(isFirstLaunch: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun saveSelectedCountry(countryCode: String) {
        dataStore.edit { prefs ->
            prefs[KEY_SELECTED_COUNTRY] = countryCode
        }
    }

}