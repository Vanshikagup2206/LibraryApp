package com.vanshika.libraryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.vanshika.libraryapp.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    var binding : ActivityAdminBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.fabAdd?.setOnClickListener {}
    }
}