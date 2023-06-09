package com.example.lifemate.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.lifemate.utils.UserPreferences

class MainViewModel (private val pref: UserPreferences) : ViewModel(){
    fun getUserToken() : LiveData<String>{
        return pref.getUserToken().asLiveData()
    }

    fun getUserUid() : LiveData<Int>{
        return pref.getUserId().asLiveData()
    }

}