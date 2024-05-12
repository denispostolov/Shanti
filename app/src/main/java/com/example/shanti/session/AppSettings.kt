package com.example.shanti.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "settings")
class AppSettings(context: Context) {
    private val dataStore = context.dataStore

    val isFirstAccess: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_FIRST_ACCESS] ?: true
    }

    suspend fun setIsFirstAccess(isFirstAccess: Boolean){
        dataStore.edit { preferences ->
            preferences[IS_FIRST_ACCESS] = isFirstAccess
        }
    }
    companion object {
        private val IS_FIRST_ACCESS = booleanPreferencesKey("is_first_access")
    }
}