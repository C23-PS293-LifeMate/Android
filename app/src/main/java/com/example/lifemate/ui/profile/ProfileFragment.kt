package com.example.lifemate.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifemate.databinding.FragmentProfileBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.authentication.UserViewModel
import com.example.lifemate.ui.changepass.ChangePassowordActivity
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.ui.personaldata.PersonalDataActivity
import com.example.lifemate.utils.Helper

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val userViewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getUserById(Helper.token, Helper.uid.toString())
        profileViewModel.userResult.observe(viewLifecycleOwner){
            binding.tvUsername.text = it.name
            binding.tvEmail.text = it.email
        }

        profileViewModel.isError.observe(viewLifecycleOwner) {
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                childFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        profileViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        binding.btnPersonalData.setOnClickListener {
            Intent(requireActivity(), PersonalDataActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnChangePass.setOnClickListener {
            Intent(requireActivity(), ChangePassowordActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnLogout.setOnClickListener{
            userViewModel.clearUserPref()
        }

    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}