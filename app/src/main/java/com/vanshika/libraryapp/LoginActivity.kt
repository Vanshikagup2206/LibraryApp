package com.vanshika.libraryapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.vanshika.libraryapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    var binding: ActivityLoginBinding? = null
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        sharedPreferences = this.getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding?.btnLogin?.setOnClickListener {
            if (binding?.etRegistrationNo?.text?.isEmpty() == true) {
                binding?.etRegistrationNo?.error =
                    resources.getString(R.string.enter_your_registration_no)
            } else if (binding?.etPassword?.text?.isEmpty() == true) {
                binding?.etPassword?.error = resources.getString(R.string.enter_password)
            } else if (binding?.rgUser?.checkedRadioButtonId == -1) {
                Toast.makeText(this, R.string.select_one, Toast.LENGTH_SHORT).show()
            } else {
                if (binding?.rbAdmin?.isChecked == true && binding?.etRegistrationNo?.text.toString().toInt() == 12200814) {
                    val intent = Intent(this, AdminMainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (binding?.rbStudent?.isChecked == true){
                    val regNo = binding?.etRegistrationNo?.text?.toString()?.trim()
                    if (regNo?.isNotEmpty() == true) {
                        sharedPreferences.edit().putString("enteredRegNo", regNo).apply()
                        Toast.makeText(this, "Reg No Saved: $regNo", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Invalid reg no", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, resources.getString(R.string.check_your_details), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}