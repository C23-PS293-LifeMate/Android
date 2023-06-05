package com.example.lifemate.ui.history

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.lifemate.data.response.RecordItem
import com.example.lifemate.data.retrofit.ApiService

class HistoryRepository(private val apiService: ApiService, private val token: String, private val uid: Int) {
    fun getRecordHistory(): LiveData<PagingData<RecordItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                HistoryPagingSource(apiService, token, uid)
            }
        ).liveData
    }
}