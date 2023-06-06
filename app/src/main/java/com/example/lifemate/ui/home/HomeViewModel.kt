package com.example.lifemate.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifemate.data.response.UserResponse
import com.example.lifemate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _userResult = MutableLiveData<UserResponse>()
    val userResult: LiveData<UserResponse> = _userResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _toPage = MutableLiveData<Boolean>()
    val toPage: LiveData<Boolean> = _toPage

    fun getUserById(token: String, id: String){
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getUserById(token, id)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    _isLoading.postValue(false)
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.idUser == id.toInt()){
                        _userResult.postValue(response.body())
                    }
                }else{
                    _isLoading.postValue(false)
                    _isError.postValue("Failed to load data")
                    Log.e(TAG, "onFailure1: Failed to load data")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.postValue(false)
                _isError.postValue("conncetion failed")
                Log.e(TAG, "onFailure2: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}