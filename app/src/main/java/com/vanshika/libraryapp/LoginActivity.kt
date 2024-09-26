package com.vanshika.libraryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.vanshika.libraryapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnLogin?.setOnClickListener {
            if (binding?.etRegistrationNo?.text?.isEmpty() == true) {
                binding?.etRegistrationNo?.error =
                    resources.getString(R.string.enter_your_registration_no)
            } else if (binding?.etPassword?.text?.isEmpty() == true) {
                binding?.etPassword?.error = resources.getString(R.string.enter_password)
            } else if (binding?.rgUser?.checkedRadioButtonId == -1) {
                Toast.makeText(this, R.string.select_one, Toast.LENGTH_SHORT).show()
            } else {
                if (binding?.rbAdmin?.isChecked == true) {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}