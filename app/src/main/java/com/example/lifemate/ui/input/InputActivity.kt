package com.example.lifemate.ui.input

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityInputBinding
import com.example.lifemate.ui.customview.CustomDialogFragment
import com.example.lifemate.ui.output.OutputActivity
import com.example.lifemate.utils.Helper

class InputActivity : AppCompatActivity() {
    private var _binding: ActivityInputBinding? = null
    private val binding get() = _binding!!
    private val inputViewModel by viewModels<InputViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        binding.edtHeight.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                if(s.isNotEmpty()){
                    binding.edtHeight.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }

            override fun afterTextChanged(s: Editable) {
                if(s.isNotEmpty()){
                    binding.edtHeight.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }

        })

        binding.edtWeight.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                if(s.isNotEmpty()){
                    binding.edtWeight.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }

            override fun afterTextChanged(s: Editable) {
                if(s.isNotEmpty()){
                    binding.edtWeight.setBackgroundResource(R.drawable.custom_edit_text)
                }
            }

        })

        binding.btnCheck.setOnClickListener {
            val height = binding.edtHeight.text.toString()
            val weight = binding.edtWeight.text.toString()
            val todoList = binding.sliderTodo.value.toString()
            val passionate = binding.sliderPassion.value.toString()
            val userHelp = binding.sliderHelp.value.toString()
            val selfReward = binding.sliderReward.value.toString()

            val isValidHeight = validateHeight(height.toString())
            val isValidWeight = validateWeight(weight.toString())

            if(isValidHeight && isValidWeight){
                inputViewModel.InsertData(Helper.token, Helper.uid, height, weight, todoList, userHelp, passionate, selfReward)
                inputViewModel.toPage.observe(this){
                    if(it == true){
                        Intent(this, OutputActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                }
            }

        }

        inputViewModel.isError.observe(this){
            val dialogFragment =
                CustomDialogFragment.newInstance(it)
            dialogFragment.show(
                supportFragmentManager,
                CustomDialogFragment::class.java.simpleName)
        }

        inputViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    private fun validateHeight(height: String): Boolean{
        if(height.isEmpty()){
            binding.edtHeight.error = "Please enter your height"
            binding.edtHeight.setBackgroundResource(R.drawable.custom_error_edit_text)
            return false
        }
        return true
    }

    private fun validateWeight(weight: String): Boolean{
        if(weight.isEmpty()){
            binding.edtWeight.error = "Please enter your weight"
            binding.edtWeight.setBackgroundResource(R.drawable.custom_error_edit_text)
            return false
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}