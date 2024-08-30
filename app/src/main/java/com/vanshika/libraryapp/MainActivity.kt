package com.vanshika.libraryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.vanshika.libraryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding ?= null
    var navController : NavController ?= null
    var appBarConfiguration : AppBarConfiguration ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navController = findNavController(R.id.host)
        binding?.bottomNavigation?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navHome -> navController?.navigate(R.id.homeFragment)
                R.id.navBooks -> navController?.navigate(R.id.booksFragment)
                R.id.navSearch -> navController?.navigate(R.id.searchFragment)
                R.id.navWishlist -> navController?.navigate(R.id.wishlistFragment)
                R.id.navProfile -> navController?.navigate(R.id.profileFragment)
            }
            return@setOnItemSelectedListener true
        }
    }
}