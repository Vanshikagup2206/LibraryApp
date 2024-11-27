package com.vanshika.libraryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.vanshika.libraryapp.databinding.ActivityAdminBinding

class AdminMainActivity : AppCompatActivity() {
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
                R.id.navBooks -> navController?.navigate(R.id.adminBooksFragment)
                R.id.navSearch -> navController?.navigate(R.id.adminSearchFragment)
                R.id.navProfile -> navController?.navigate(R.id.adminProfileFragment)
            }
            return@setOnItemSelectedListener true
        }
    }
}