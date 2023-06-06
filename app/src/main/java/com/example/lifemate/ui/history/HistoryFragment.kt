package com.example.lifemate.ui.history

import android.os.Bundle
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
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.utils.Helper

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModels()
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
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                childFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        historyViewModel.getRecordById(Helper.token, Helper.uid)
        historyViewModel.listHistory.observe(requireActivity()){
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.rvHistory.layoutManager = layoutManager
            binding.rvHistory.addItemDecoration(DividerItemDecoration(requireActivity(), layoutManager.orientation))
            setHistoryData(it)
        }
    }

    private fun setHistoryData(historyList: List<RecordItem>){
        val adapter = HistoryAdapter(historyList)
        binding.rvHistory.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}