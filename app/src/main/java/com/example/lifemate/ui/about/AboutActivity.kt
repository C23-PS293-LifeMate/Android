package com.example.lifemate.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifemate.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private var _binding: ActivityAboutBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}