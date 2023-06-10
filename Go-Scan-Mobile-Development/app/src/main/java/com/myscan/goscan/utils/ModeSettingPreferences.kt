package com.myscan.goscan.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

class ModeSettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_SET = booleanPreferencesKey(THEME_KEY)

    fun fetchTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_SET] ?: false
        }
    }

    suspend fun setTheme(activateMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_SET] = activateMode
        }
    }

    companion object {
        const val THEME_KEY = "theme_setting"

        @Volatile
        private var INSTANCE: ModeSettingPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): ModeSettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ModeSettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}