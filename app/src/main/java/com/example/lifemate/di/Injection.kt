package com.example.lifemate.di

import android.content.Context
import com.example.lifemate.data.retrofit.ApiConfig
import com.example.lifemate.data.retrofit.ApiService
import com.example.lifemate.ui.history.HistoryRepository
import com.example.lifemate.utils.Helper
import com.example.lifemate.utils.UserPreferences
import com.example.lifemate.utils.dataStore

object Injection {
    fun providePreferences(context: Context): UserPreferences {
        return UserPreferences.getInstance(context.dataStore)
    }

    fun provideRepository(apiService: ApiService,token: String, uid: Int): HistoryRepository{
        return HistoryRepository(apiService, token, uid)
    }
}