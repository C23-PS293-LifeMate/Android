package com.example.lifemate.ui.output

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityOutputBinding
import com.example.lifemate.utils.Helper.withHistoryDayDateFormat

class OutputActivity : AppCompatActivity() {

    private var _binding : ActivityOutputBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = intent.getStringExtra(EXTRA_KEY_DATE)?.withHistoryDayDateFormat()

        binding.tvBmiLevel.text = intent.getIntExtra(EXTRA_KEY_BMI,0).toString()
        binding.tvStressScore.text = intent.getFloatExtra(EXTRA_KEY_STRESS,0.0f).toString()



        binding.btnClose.setOnClickListener{
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val EXTRA_KEY_BMI = "KEY_BMI"
        const val EXTRA_KEY_STRESS = "KEY_STRESS"
        const val EXTRA_KEY_DATE = "KEY_DATE"


    }
}