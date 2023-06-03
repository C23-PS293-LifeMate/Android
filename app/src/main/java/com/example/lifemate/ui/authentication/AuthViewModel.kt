package com.example.lifemate.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifemate.data.response.LoginResponse
import com.example.lifemate.data.response.RegisterResponse
import com.example.lifemate.data.response.UpdateResponse
import com.example.lifemate.data.response.UserResponse
import com.example.lifemate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel: ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _toPage = MutableLiveData<Boolean>()
    val toPage: LiveData<Boolean> = _toPage

    fun loginResponse(email: String,password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message == "Login successful"){
                        _loginResult.postValue(response.body())
                    }
                    else{
//                        _isError.value = "ERROR ${response.code()} : ${response.message()}"
                        _isError.value = "Wrong password / email"
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.postValue(t.message)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun registerResponse(name: String, email: String, password: String, bod: String, gender: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(name, email, password, bod, gender)

        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message == "User Created"){
                        _toPage.value = true
                        _isError.value = "Register successful"
                        _registerResult.postValue(response.body())
                    }else{
                        _toPage.value = false
                        _isError.value = response.body()?.message ?: "Error"
                    }
                }else{
                    _toPage.value = false
                    _isLoading.value = false
                    _isError.value = response.body()?.message ?: "Error"
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _toPage.value = false
                _isLoading.value = false
                _isError.value = t.message
                Log.e(TAG, "onFailure2: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}