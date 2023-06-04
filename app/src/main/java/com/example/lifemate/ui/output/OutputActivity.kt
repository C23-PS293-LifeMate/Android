package com.example.lifemate.ui.output

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityOutputBinding

class OutputActivity : AppCompatActivity() {

    private var _binding : ActivityOutputBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener{
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}