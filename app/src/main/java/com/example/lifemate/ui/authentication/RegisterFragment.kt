package com.example.lifemate.ui.authentication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.lifemate.R
import com.example.lifemate.databinding.FragmentRegisterBinding
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.utils.Helper
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()
    private var genderText: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogin.setOnClickListener{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.container, LoginFragment(), LoginFragment::class.java.simpleName)
                commit()
            }
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, gender)
        binding.genderSpinner.adapter = arrayAdapter
        binding.genderSpinner.onItemSelectedListener = this

        binding.edtBirthdate.setOnClickListener{
            Helper.setDate(requireContext(), it as EditText)
        }

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

        binding.btnRegister.setOnClickListener{
            val name = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPass.text.toString()
            val dob = binding.edtBirthdate.text.toString()

            val isValidName = validateUsername(name)
            val isValidEmail = validateEmail(email)
            val isValidPassword = validatePassword(password)
            val isValidDob = validateBirthdate(dob)
            val isValidGender = validateGender(genderText,gender)

            if(isValidName&&isValidEmail&&isValidPassword&&isValidDob&&isValidGender){
                authViewModel.registerResponse(name,email,password,dob,genderText)
                authViewModel.toPage.observe(viewLifecycleOwner){
                    if (it == true){
                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.container, LoginFragment(), LoginFragment::class.java.simpleName)
                            commit()
                        }
                    }
                }
            }
        }

        authViewModel.isError.observe(viewLifecycleOwner) {
            //Nampilin error pake ini "it" parameter stringny
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                childFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        authViewModel.isLoading.observe(viewLifecycleOwner) {
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

    private fun validateGender(sex: String, gender:Array<String>): Boolean{
        return if(sex == gender[0]){
            binding.genderSpinner.setBackgroundResource(R.drawable.custom_error_edit_text)
            false
        }else{
            binding.genderSpinner.setBackgroundResource(R.drawable.custom_edit_text)
            true
        }
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

    private fun validatePassword(password: String): Boolean {
        if (password.isEmpty()) {
            binding.edtPass.error = "Password cannot be empty"
            binding.edtPass.setBackgroundResource(R.drawable.custom_error_edit_text)
            return false
        }
        if (password.length < 8) {
            binding.edtPass.error = "Password must atleast 8 character long"
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

//    private fun setDate(){
//        var calendar = Calendar.getInstance()
//
//        val month = calendar.get(Calendar.MONTH)
//        val year = calendar.get(Calendar.YEAR)
//        val date = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datepicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
//            calendar.set(Calendar.YEAR, year)
//            calendar.set(Calendar.MONTH, month)
//            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            updateLable(calendar)
//        }
//
//        binding.edtBirthdate.setOnClickListener{
//            DatePickerDialog(requireActivity(), datepicker, year, month, date).show()
//        }
//    }
//
//    private fun updateLable(calendar: Calendar){
//        val myFormat = "yyyy-MM-dd"
//        val sdf = SimpleDateFormat(myFormat, Locale.UK)
//        binding.edtBirthdate.setText(sdf.format(calendar.time))
//    }

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
        //Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}