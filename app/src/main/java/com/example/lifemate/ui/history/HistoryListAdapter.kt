package com.example.lifemate.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lifemate.data.response.RecordItem
import com.example.lifemate.databinding.HistoryItemBinding
import com.example.lifemate.utils.Helper.withDateFormat
import com.example.lifemate.utils.Helper.withHistoryDateFormat

class HistoryListAdapter : PagingDataAdapter<RecordItem, HistoryListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }*/
        getItem(position)?.let { holder.bind(it) }
    }

    class MyViewHolder(private val binding: HistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(data: RecordItem){
            binding.tvDay.text = data.recordDate.withHistoryDateFormat()
            binding.tvDate.text = data.recordDate.withDateFormat()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecordItem>() {
            override fun areItemsTheSame(oldItem: RecordItem, newItem: RecordItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecordItem, newItem: RecordItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}