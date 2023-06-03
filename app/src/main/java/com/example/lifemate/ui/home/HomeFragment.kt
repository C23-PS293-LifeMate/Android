package com.example.lifemate.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentHistoryBinding
import com.example.lifemate.databinding.FragmentHomeBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.authentication.UserViewModel
import com.example.lifemate.ui.input.InputActivity
import com.example.lifemate.ui.personaldata.PersonalDataActivity
import com.example.lifemate.ui.profile.ProfileViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val userViewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        userViewModel.getUserToken().observe(viewLifecycleOwner) { utoken ->
            userViewModel.getUserId().observe(viewLifecycleOwner) { uid ->
                profileViewModel.getUserById(utoken, uid.toString())
                profileViewModel.userResult.observe(viewLifecycleOwner){
                    binding.tvGreet.setText(resources.getString(R.string.greeting, it.name))
                }
            }
        }

        binding.btnCheck.setOnClickListener {
            Intent(requireActivity(), InputActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}