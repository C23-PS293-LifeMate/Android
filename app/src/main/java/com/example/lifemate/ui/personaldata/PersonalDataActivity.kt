package com.example.lifemate.ui.personaldata

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityPersonalDataBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.authentication.AuthViewModel
import com.example.lifemate.ui.authentication.UserViewModel
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.utils.Helper
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PersonalDataActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var _binding: ActivityPersonalDataBinding? = null
    private val binding get() = _binding!!
    private var genderText: String = ""

    private val authViewModel by viewModels<AuthViewModel>()
    private val userViewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, gender)
        binding.genderSpinner.adapter = arrayAdapter
        binding.genderSpinner.onItemSelectedListener = this

        binding.edtUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.isNotEmpty()){
                    binding.edtUsername.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }
            override fun afterTextChanged(s: Editable) {
                if(s.isNotEmpty()){
                    binding.edtUsername.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }
        })

        binding.edtBirthdate.setOnClickListener{
            Helper.setDate(this, it as EditText)
        }

        binding.edtBirthdate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.isNotEmpty()){
                    binding.edtUsername.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }
            override fun afterTextChanged(s: Editable) {
                if(s.isNotEmpty()){
                    binding.edtUsername.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }
        })

        userViewModel.getUserToken().observe(this){utoken ->
            userViewModel.getUserId().observe(this){uid ->

                authViewModel.getUserById(utoken, uid.toString())
                authViewModel.userResult.observe(this){
                    binding.edtUsername.setText(it.name)
                    binding.edtEmail.setText(it.email)
                    binding.edtBirthdate.setText(Helper.formatDate(it.birthDate.toString()))
                    binding.genderSpinner.setSelection(if(it.gender == "laki-laki") 1 else 2)
                }

                binding.btnSave.setOnClickListener{
                    val name = binding.edtUsername.text.toString()
                    val email = binding.edtEmail.text.toString()
                    val dob = binding.edtBirthdate.text.toString()

                    val isValidName = validateUsername(name)
                    val isValidEmail = validateEmail(email)
                    val isValidDob = validateBirthdate(dob)
                    val isValidGender = validateGender(genderText,gender)

                    if(isValidName&&isValidEmail&&isValidDob&&isValidGender){
                        authViewModel.UpdateResponse(utoken, uid, name, email, dob, genderText)
                        authViewModel.toPage.observe(this){
                            if(it == true) finish()
                        }
                    }
                }

            }
        }


        authViewModel.isError.observe(this) {
            //Nampilin error pake ini "it" parameter stringny
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                supportFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        authViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun validateUsername(email: String): Boolean {
        if (email.isEmpty()) {
            binding.edtUsername.error = "Username cannot be empty"
            binding.edtUsername.setBackgroundResource(R.drawable.custom_error_edit_text)
            return false
        }
        return true
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            binding.edtEmail.error = "Email cannot be empty"
            binding.edtEmail.setBackgroundResource(R.drawable.custom_error_edit_text)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Email format wrong"
            return false
        }
        return true
    }

    private fun validateBirthdate(password: String): Boolean {
        if (password.isEmpty()) {
            binding.edtBirthdate.error = "Date of birth cannot be empty"
            binding.edtBirthdate.setBackgroundResource(R.drawable.custom_error_edit_text)
            return false
        }
        return true
    }

    private fun validateGender(sex: String, gender:Array<String>): Boolean{
        return if(sex == gender[0]){
            binding.genderSpinner.setBackgroundResource(R.drawable.custom_error_edit_text)
            false
        }else{
            binding.genderSpinner.setBackgroundResource(R.drawable.custom_edit_text)
            true
        }
    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        genderText = parent?.getItemAtPosition(position).toString()
        genderText = when(genderText){
            "Male" -> "laki-laki"
            "Female" -> "perempuan"
            else -> genderText
        }
        if (position == 1 || position == 2){
            binding.genderSpinner.setBackgroundResource(R.drawable.custom_edit_text)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}