package com.example.lifemate.ui.input

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityInputBinding
import com.example.lifemate.ui.output.OutputActivity

class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {
            Intent(this, OutputActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}