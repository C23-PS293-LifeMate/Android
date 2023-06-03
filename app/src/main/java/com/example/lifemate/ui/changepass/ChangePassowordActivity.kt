package com.example.lifemate.ui.changepass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityChangePassowordBinding
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.utils.Helper

class ChangePassowordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePassowordBinding
    private val changePasswordViewModel by viewModels<ChangePassViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassowordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.edtNewPassAgain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val input = s.toString();
                if (input != binding.edtNewPass.text.toString()){
                    binding.edtNewPassAgain.error = "Password do not match"
                    binding.edtNewPassAgain.setBackgroundResource(R.drawable.custom_error_edit_text)
                }
                else{
                    Log.d("test3", binding.edtNewPass.text.toString())
                    binding.edtNewPassAgain.error = null
                    binding.edtNewPassAgain.setBackgroundResource(R.drawable.custom_edit_text)
                }

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0 == binding.edtNewPass.text){
                    binding.edtNewPassAgain.setBackgroundResource(R.drawable.custom_edit_text)
                }

            }
        })

        binding.edtCurrentPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.edtCurrentPass.setBackgroundResource(R.drawable.custom_edit_text)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.btnSave.setOnClickListener {
            val newAgain = binding.edtNewPass.text.toString()
            val current = binding.edtCurrentPass.text.toString()
            val new = binding.edtNewPass.text.toString()

            val isValidNewAgain = validateNewPass(newAgain)
            val isValidPassword = validatePassword(new)

            if(isValidNewAgain && isValidPassword){
                changePasswordViewModel.changeResponse(Helper.token, Helper.uid, current, new)
                changePasswordViewModel.toPage.observe(this){
                    if(it == true) finish()
                }
            }

        }

        changePasswordViewModel.isError.observe(this) {
            //Nampilin error pake ini "it" parameter stringny
            if(it == "Invalid current password"){
                binding.edtCurrentPass.error = "Invalid current password"
                binding.edtCurrentPass.setBackgroundResource(R.drawable.custom_error_edit_text)
            }
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                supportFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        changePasswordViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun validateNewPass(newPass: String): Boolean{
        return if(newPass != binding.edtNewPass.text.toString()){
            binding.edtNewPassAgain.error = "Password do not match"
            binding.edtNewPassAgain.setBackgroundResource(R.drawable.custom_error_edit_text)
            false
        } else{
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            binding.edtNewPass.error = "New password cannot be empty"
            return false
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}
}