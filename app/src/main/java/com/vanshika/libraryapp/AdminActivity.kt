package com.vanshika.libraryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.vanshika.libraryapp.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    var binding : ActivityAdminBinding ?= null
    var navController : NavController ?= null
    var appBarConfiguration : AppBarConfiguration ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navController = findNavController(R.id.adminHost)
        binding?.bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navHome -> navController?.navigate(R.id.adminHomeFragment)
            }
            return@setOnItemSelectedListener true
        }
    }
}