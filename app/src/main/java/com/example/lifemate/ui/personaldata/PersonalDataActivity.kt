package com.example.lifemate.ui.personaldata

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityPersonalDataBinding
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.ui.profile.ProfileViewModel
import com.example.lifemate.utils.Helper
import com.example.lifemate.utils.Helper.uid
import com.example.lifemate.utils.Helper.withDateFormat
import java.util.*

class PersonalDataActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var _binding: ActivityPersonalDataBinding? = null
    private val binding get() = _binding!!
    private var genderText: String = ""

    private val profileViewModel by viewModels<ProfileViewModel>()
//    private val userViewModel by viewModels<UserViewModel> {
//        ViewModelFactory.getInstance(this)
//    }

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
                val input = s.toString();
                binding.edtAge.setText(Helper.getAge(input).toString())
                if(s.isNotEmpty()){
                    binding.edtUsername.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }
            override fun afterTextChanged(s: Editable) {
                val input = s.toString();
                binding.edtAge.setText(Helper.getAge(input).toString())
                if(s.isNotEmpty()){
                    binding.edtUsername.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }
        })


        profileViewModel.getUserById(Helper.token, uid.toString())
        profileViewModel.userResult.observe(this){
            binding.edtUsername.setText(it.name)
            binding.edtEmail.setText(it.email)
            binding.edtBirthdate.setText(it.birthDate.withDateFormat())
            binding.genderSpinner.setSelection(if(it.gender == "laki-laki") 1 else 2)
            binding.edtAge.setText(Helper.getAge(it.birthDate).toString())

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
                profileViewModel.UpdateResponse(Helper.token, uid, name, email, dob, genderText)
                profileViewModel.toPage.observe(this){
                    if(it == true) finish()
                }
            }
        }

        profileViewModel.isError.observe(this) {
            //Nampilin error pake ini "it" parameter stringny
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                supportFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        profileViewModel.isLoading.observe(this) {
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