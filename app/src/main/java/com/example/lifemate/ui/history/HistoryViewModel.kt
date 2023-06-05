package com.example.lifemate.ui.history

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lifemate.data.response.RecordItem
import com.example.lifemate.data.retrofit.ApiService
import com.example.lifemate.di.Injection

class HistoryViewModel(historyRepository: HistoryRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    val recordHistory: LiveData<PagingData<RecordItem>> =
        historyRepository.getRecordHistory().cachedIn(viewModelScope)
}

class HistoryViewModelFactory(private val apiService: ApiService, private val token: String, private val uid: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(Injection.provideRepository(apiService, token, uid)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}