package com.example.lifemate.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifemate.data.response.RecordItem
import com.example.lifemate.databinding.HistoryItemBinding
import com.example.lifemate.ui.output.OutputActivity
import com.example.lifemate.utils.Helper.withHistoryDateFormat
import com.example.lifemate.utils.Helper.withHistoryDayFormat

class HistoryAdapter(private val listHistory: List<RecordItem>, private val listener: OnRecordDeleteListener) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        val day: TextView = binding.tvDay
        val date: TextView = binding.tvDate
        val btnDlt: ImageView = binding.btnDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = listHistory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = listHistory[position]
        holder.day.text = history.recordDate.withHistoryDayFormat()
        holder.date.text = history.recordDate.withHistoryDateFormat()

        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, OutputActivity::class.java)
            intentDetail.putExtra(OutputActivity.EXTRA_KEY_BMI, history.obesity)
            intentDetail.putExtra(OutputActivity.EXTRA_KEY_STRESS, history.stress)
            intentDetail.putExtra(OutputActivity.EXTRA_KEY_DATE, history.recordDate)
            holder.itemView.context.startActivity(intentDetail)
        }

        holder.btnDlt.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                val recordId = listHistory[position].id
                listener.onRecordDelete(recordId)
            }
        }
    }

    interface OnRecordDeleteListener {
        fun onRecordDelete(recordId: Int)
    }
}