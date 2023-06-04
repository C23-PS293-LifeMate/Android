package com.example.lifemate.ui.input

import android.renderscript.ScriptGroup.Input
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifemate.data.response.InsertRecord
import com.example.lifemate.data.response.UserResponse
import com.example.lifemate.data.retrofit.ApiConfig
import com.example.lifemate.ui.profile.ProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputViewModel: ViewModel() {

    private val _insertData = MutableLiveData<InsertRecord>()
    val insertData: LiveData<InsertRecord> = _insertData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _toPage = MutableLiveData<Boolean>()
    val toPage: LiveData<Boolean> = _toPage

    fun InsertData(token: String, uid: Int, height: String, weight: String, toDoList: String, userHelp: String, passionate: String, selfReward: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().insertRecord(token, uid, height, weight, toDoList, userHelp, passionate, selfReward)

        client.enqueue(object : Callback<InsertRecord>{
            override fun onResponse(call: Call<InsertRecord>, response: Response<InsertRecord>) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message == "Record inserted"){
                        _toPage.value = true
                        _insertData.postValue(response.body())
                    }
                    else{
                        _toPage.value = false
                        _isError.value = responseBody?.message ?: "Error"
                    }
                }else{
                    _isLoading.value = false
                    _toPage.value = false
                    _isError.value = response.body()?.message ?: "Error"
                    Log.e(TAG, "onFailure1: ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<InsertRecord>, t: Throwable) {
                _isLoading.value = false
                _toPage.value = false
                _isError.value = t.message
                Log.e(TAG, "onFailure2: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "InputViewModel"
    }
}