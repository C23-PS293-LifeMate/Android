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

        val bmi = intent.getIntExtra(EXTRA_KEY_BMI,0)
        val stress = intent.getFloatExtra(EXTRA_KEY_STRESS,0.0f)

        supportActionBar?.title = intent.getStringExtra(EXTRA_KEY_DATE)?.withHistoryDayDateFormat()

        binding.tvBmiLevel.text = bmi.toString()
        binding.tvStressScore.text = stress.toString()


        if(stress > 5){
            binding.stressTvTittle.text = resources.getString(R.string.stress_bad_tittle)
            binding.stressTv.text = resources.getString(R.string.stress_bad)
        }else{
            binding.stressTvTittle.text = resources.getString(R.string.stress_good_tittle)
            binding.stressTv.text = resources.getString(R.string.stress_good)
        }

        when(intent.getIntExtra(EXTRA_KEY_BMI,0)){
            1 -> {binding.bmiTvTittle.text = resources.getString(R.string.tittle_1)
                binding.bmiTv.text = resources.getString(R.string.text_1)}
            2 -> {binding.bmiTvTittle.text = resources.getString(R.string.tittle_2)
                binding.bmiTv.text = resources.getString(R.string.text_2)}
            3 -> {binding.bmiTvTittle.text = resources.getString(R.string.tittle_3)
                binding.bmiTv.text = resources.getString(R.string.text_3)}
            4 -> {binding.bmiTvTittle.text = resources.getString(R.string.tittle_4)
                binding.bmiTv.text = resources.getString(R.string.text_4)}
            5 -> {binding.bmiTvTittle.text = resources.getString(R.string.tittle_5)
                binding.bmiTv.text = resources.getString(R.string.text_5)}
            6 -> {binding.bmiTvTittle.text = resources.getString(R.string.tittle_6)
                binding.bmiTv.text = resources.getString(R.string.text_6)}
        }



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