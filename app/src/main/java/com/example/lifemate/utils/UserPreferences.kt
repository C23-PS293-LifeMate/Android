package com.example.lifemate.utils

import android.content.Context
import android.service.autofill.UserData
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifemate.ui.authentication.UserViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){
    val token = stringPreferencesKey("Token")
    val uid = intPreferencesKey("UID")
//    private val isLogin = booleanPreferencesKey("false")

    fun getUserId(): Flow<Int> = dataStore.data.map { it[uid] ?: -1 }


    fun getUserToken(): Flow<String> = dataStore.data.map { it[token] ?: "token" }

    suspend fun saveUserPref(tokenUser:String, uidUser:Int){
        dataStore.edit { preferences ->
            preferences[token] = tokenUser
            preferences[uid] = uidUser
        }
    }

    suspend fun clearUserPref() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}