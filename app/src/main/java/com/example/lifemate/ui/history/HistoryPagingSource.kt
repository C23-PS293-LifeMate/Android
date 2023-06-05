package com.example.lifemate.ui.history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lifemate.data.response.RecordItem
import com.example.lifemate.data.retrofit.ApiService
import com.example.lifemate.utils.Helper

class HistoryPagingSource(private val apiService: ApiService, private val token: String, private val uid: Int): PagingSource<Int, RecordItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, RecordItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecordItem> {
        return try{
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getRecordById(token, uid, position, params.loadSize).result

            LoadResult.Page(
                data = responseData,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        }catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }
}