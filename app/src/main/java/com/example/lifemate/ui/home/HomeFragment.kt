package com.example.lifemate.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentHomeBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.authentication.UserViewModel
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.ui.input.InputActivity
import com.example.lifemate.ui.profile.ProfileViewModel
import com.example.lifemate.utils.Helper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    private val userViewModel by viewModels<UserViewModel> {
//        ViewModelFactory.getInstance(requireActivity())
//    }
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getUserById(Helper.token, Helper.uid.toString())
        profileViewModel.userResult.observe(viewLifecycleOwner){
            binding.tvGreet.text = resources.getString(R.string.greeting, it.name)
        }

        binding.btnCheck.setOnClickListener {
            Intent(requireActivity(), InputActivity::class.java).also {
                startActivity(it)
            }
        }

        profileViewModel.isError.observe(viewLifecycleOwner){
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                childFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        profileViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.tvGreet.text = resources.getString(R.string.greeting, "")
        }
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}