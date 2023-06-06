package com.example.lifemate.ui.changepass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifemate.data.response.ChangePasswordResponse
import com.example.lifemate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassViewModel: ViewModel() {

    private val _changeResult = MutableLiveData<ChangePasswordResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _toPage = MutableLiveData<Boolean>()
    val toPage: LiveData<Boolean> = _toPage

    fun changeResponse(token: String, id: Int, current: String, new: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().changePassword(token, id, current, new)

        client.enqueue(object : Callback<ChangePasswordResponse> {
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message == "Password changed successfully"){
                        _toPage.value = true
                        _changeResult.postValue(response.body())
                    }else{
                        _toPage.value = false
                        _isError.value = response.body()?.message
                    }
                }else{
                    _toPage.value = false
                    _isLoading.value = false
                    _isError.value = response.body()?.message ?: "Error"
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                _toPage.value = false
                _isLoading.value = false
                _isError.value = t.message
                Log.e(TAG, "onFailure2: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "ChangePassViewModel"
    }
}