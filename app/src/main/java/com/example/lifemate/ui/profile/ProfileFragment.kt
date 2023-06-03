package com.example.lifemate.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentLoginBinding
import com.example.lifemate.databinding.FragmentProfileBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.authentication.AuthViewModel
import com.example.lifemate.ui.authentication.UserViewModel
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.ui.main.MainActivity
import com.example.lifemate.ui.personaldata.PersonalDataActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
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

        userViewModel.getUserToken().observe(viewLifecycleOwner) { utoken ->
            userViewModel.getUserId().observe(viewLifecycleOwner) { uid ->
                authViewModel.getUserById(utoken, uid.toString())
                authViewModel.userResult.observe(viewLifecycleOwner){
                    binding.tvUsername.text = it.name
                    binding.tvEmail.text = it.email
                }
            }
        }

        authViewModel.isError.observe(viewLifecycleOwner) {
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                childFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        authViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        binding.btnPersonalData.setOnClickListener {
            Intent(requireActivity(), PersonalDataActivity::class.java).also {
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