package com.example.lifemate.ui.personaldata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifemate.data.response.UpdateResponse
import com.example.lifemate.data.response.UserResponse
import com.example.lifemate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalDataViewModel: ViewModel() {
    private val _updateResult = MutableLiveData<UpdateResponse>()

    private val _userResult = MutableLiveData<UserResponse>()
    val userResult: LiveData<UserResponse> = _userResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _toPage = MutableLiveData<Boolean>()
    val toPage: LiveData<Boolean> = _toPage

    fun UpdateResponse(token: String, id: Int, name: String, email: String, birthDate: String, gender: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().UpdateUser(token, id, name, email, birthDate, gender)

        client.enqueue(object : Callback<UpdateResponse> {
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message == "User data updated successfully"){
                        _toPage.value = true
                        _updateResult.postValue(response.body())
                    }else{
                        _toPage.value = false
                        _isError.value = response.body()?.message ?: "Email is already taken"
                    }
                }else{
                    _toPage.value = false
                    _isLoading.value = false
                    _isError.value = response.body()?.message ?: "Error"
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                _toPage.value = false
                _isLoading.value = false
                _isError.value = t.message
                Log.e(TAG, "onFailure2: ${t.message.toString()}")
            }

        })
    }

    fun getUserById(token: String, id: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserById(token, id)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.idUser == id.toInt()){
                        _userResult.postValue(response.body())
                    }
                }else{
                    _isLoading.value = false
                    _isError.value = "Failed to load data"
                    Log.e(TAG, "onFailure1: Failed to load data")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.postValue("conncetion failed")
                Log.e(TAG, "onFailure1: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "PersonalDataViewModel"
    }
}