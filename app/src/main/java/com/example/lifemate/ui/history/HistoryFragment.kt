package com.example.lifemate.ui.history

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifemate.R
import com.example.lifemate.data.retrofit.ApiConfig
import com.example.lifemate.databinding.FragmentHistoryBinding
import com.example.lifemate.databinding.FragmentProfileBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.utils.Helper

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(ApiConfig.getApiService(), Helper.token, Helper.uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.show()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistory.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = HistoryListAdapter()
        binding.rvHistory.adapter = adapter
        historyViewModel.recordHistory.observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun getData() {
        val adapter = HistoryListAdapter()
        binding.rvHistory.adapter = adapter
        historyViewModel.recordHistory.observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = null
    }
}