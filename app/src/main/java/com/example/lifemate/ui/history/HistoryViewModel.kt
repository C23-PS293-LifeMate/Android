package com.example.lifemate.ui.history

import android.util.Log
import androidx.lifecycle.*
import com.example.lifemate.data.response.DeleteResponse
import com.example.lifemate.data.response.RecordItem
import com.example.lifemate.data.response.RecordResponse
import com.example.lifemate.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel: ViewModel() {

    private val _listHistory = MutableLiveData<List<RecordItem>>()
    val listHistory: LiveData<List<RecordItem>> = _listHistory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String> = _isError

    fun getRecordById(token: String, id:Int){
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getRecordById(token, id)

        client.enqueue(object : Callback<RecordResponse>{
            override fun onResponse(
                call: Call<RecordResponse>,
                response: Response<RecordResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.postValue(false)
                    val responseBody = response.body()
                    if(responseBody != null){
                        _listHistory.postValue(responseBody.result)
                    }else{
                        _isError.postValue("Failed to load data")
                    }
                }else{
                    _isLoading.postValue(false)// = false
                    _isError.postValue(response.message()) //= "Failed to load data"
                    Log.e(TAG, "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecordResponse>, t: Throwable) {
                _isLoading.postValue(false)// = false
                _isError.postValue("conncetion failed")
                Log.e(TAG, "onFailure2: ${t.message.toString()}")
            }

        })
    }

    fun deleteRecordById(token: String, id: Int){
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().deleteRecordById(token, id)

        client.enqueue(object : Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.postValue(false)
                    val responseBody = response.body()
                    if(responseBody != null && responseBody.message == "Record deleted"){
                        _isError.postValue("Data deleted")
                    }else{
                        _isError.postValue("Failed to delete data")
                    }
                }else{
                    _isLoading.postValue(false)
                    _isError.postValue(response.message())
                    Log.e(TAG, "On Failure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                _isLoading.postValue(false)
                _isError.postValue("conncetion failed")
                Log.e(TAG, "On Failure2: ${t.message}")
            }

        })
    }



    companion object {
        private const val TAG = "HistoryViewModel"
    }
}
