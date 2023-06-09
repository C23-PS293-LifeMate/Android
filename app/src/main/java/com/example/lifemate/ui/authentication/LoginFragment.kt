package com.example.lifemate.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifemate.ui.main.MainActivity
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentLoginBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.utils.Helper

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()
    private val userViewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel.loginResult.observe(viewLifecycleOwner) {
            userViewModel.saveUserPref(
                it.token,
                it.idUser
            )
            Helper.token = it.token
            Helper.uid = it.idUser
        }

        userViewModel.getUserId().observe(viewLifecycleOwner){
            Helper.uid = it
            Log.d("test", Helper.uid.toString())
        }

        userViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
            if (token != "token") {

                Helper.token = token
                Log.d("test", Helper.token)
                Intent(requireActivity(), MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        binding.edtPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.toString().isNotEmpty()){
                    if(s.toString().length < 8){
                        binding.edtlPass.setPasswordVisibilityToggleEnabled(false)
                    }else{
                        binding.edtlPass.setPasswordVisibilityToggleEnabled(true)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.btnLogin.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPass.text.toString()

            val isValidEmail = validateEmail(email)
            val isValidPassword = validatePassword(password)

            if (isValidEmail && isValidPassword) {
                authViewModel.loginResponse(email, password)
            }

        }

        authViewModel.isError.observe(viewLifecycleOwner) {
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                childFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        authViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.tvRegister.setOnClickListener{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.container, RegisterFragment(), RegisterFragment::class.java.simpleName)
                addSharedElement(binding.logoLifemate,"logo")
                addSharedElement(binding.textView,"tittle")
                addSharedElement(binding.layout,"layout")
                commit()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            binding.edtEmail.error = "Email cannot be empty"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Email format wrong"
            return false
        }
        return true
    }

    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            binding.edtPass.error = "Password cannot be empty"
            return false
        }
        if (password.length < 8) {
            binding.edtPass.error = "Password must atleast 8 character long"
            return false
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}