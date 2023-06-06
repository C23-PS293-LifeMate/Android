package com.example.lifemate.ui.input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lifemate.data.response.InsertRecord
import com.example.lifemate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputViewModel: ViewModel() {

    private val _insertResponse = MutableLiveData<InsertRecord>()
    val insertResponse: LiveData<InsertRecord> = _insertResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    private val _toPage = MutableLiveData<Boolean>()
    val toPage: LiveData<Boolean> = _toPage

    fun insertData(token: String, uid: Int, height: String, weight: String, toDoList: String, userHelp: String, passionate: String, selfReward: String){
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().insertRecord(token, uid, height, weight, toDoList, userHelp, passionate, selfReward)

        client.enqueue(object : Callback<InsertRecord>{
            override fun onResponse(call: Call<InsertRecord>, response: Response<InsertRecord>) {
                if(response.isSuccessful){
                    _isLoading.postValue(false)
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message == "Record inserted"){
                        _toPage.postValue(true)
                        _insertResponse.postValue(response.body())
                    }
                    else{
                        _toPage.postValue(false)
                        _isError.postValue(responseBody?.message ?: "Error")
                    }
                }else{
                    _isLoading.postValue(false)
                    _toPage.postValue(false)
                    _isError.postValue(response.body()?.message ?: "Error")
                    Log.e(TAG, "onFailure1: ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<InsertRecord>, t: Throwable) {
                _isLoading.postValue(false)
                _toPage.postValue(false)
                _isError.postValue(t.message)
                Log.e(TAG, "onFailure2: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "InputViewModel"
    }
}