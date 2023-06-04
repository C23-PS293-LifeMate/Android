package com.example.lifemate.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lifemate.R
import com.example.lifemate.databinding.ActivityMainBinding
import com.example.lifemate.ui.ViewModelFactory
import com.example.lifemate.ui.authentication.AuthenticationActivity
import com.example.lifemate.ui.authentication.UserViewModel
import com.example.lifemate.utils.Helper
import com.example.lifemate.utils.Helper.token

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun setupViewModel() {
        mainViewModel.getUserToken().observe(this){Token ->
            if (Token != "token") {
                token = Token
                Log.d("test2", Helper.token)
                mainViewModel.getUserUid().observe(this){
                    if(it != -1){
                        Helper.uid = it
                        Log.d("test2", Helper.uid.toString())
                    }else {
                        startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                        finish()
                    }
                }
            }else{
                startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}