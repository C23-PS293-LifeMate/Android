package com.example.lifemate.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifemate.data.response.RecordItem
import com.example.lifemate.databinding.FragmentHistoryBinding
import com.example.lifemate.ui.customview.ConnectionFailedDialogFragment
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.utils.Helper

class HistoryFragment : Fragment(),HistoryAdapter.OnRecordDeleteListener , ConnectionFailedDialogFragment.RefreshListener{

    private lateinit var binding: FragmentHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }

        historyViewModel.isError.observe(requireActivity()){
            when (it) {
                "Data deleted" -> {
                    Log.d("test", "jalan")
                    adapter.notifyDataSetChanged()
                    historyViewModel.getRecordById(Helper.token, Helper.uid)
                }
                "conncetion failed" -> {
                    val dialogFragment = ConnectionFailedDialogFragment()
                    dialogFragment.setRefreshListener(this)
                    dialogFragment.show(childFragmentManager, "ConnectionFailedDialogFragment")
                }
                else -> {
                    val dialogFragment = CustomDialogFragment.newInstance(it)
                    dialogFragment.show(
                        childFragmentManager,
                        CustomDialogFragment::class.java.simpleName
                    )
                }
            }
        }

        historyViewModel.getRecordById(Helper.token, Helper.uid)
        historyViewModel.listHistory.observe(viewLifecycleOwner){
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvHistory.layoutManager = layoutManager
            if(!it.isNullOrEmpty()){
                binding.rvHistory.visibility = View.VISIBLE
                setHistoryData(it)

            }else{
                binding.rvHistory.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            }

        }
    }

    private fun setHistoryData(historyList: List<RecordItem>){
        val sortedList = historyList.sortedByDescending { it.recordDate }
        adapter = HistoryAdapter(sortedList, this)
        binding.rvHistory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onRecordDelete(recordId: Int) {
        historyViewModel.deleteRecordById(Helper.token,recordId)
    }

    override fun onRefresh() {
        historyViewModel.getRecordById(Helper.token, Helper.uid)
    }
}