package com.kundalik.billbook.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImg: Uri
    private lateinit var dialog: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvAlreadyHaveAccount.setOnClickListener{
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            finish()
        }

        binding.btnContinueRegistration.setOnClickListener {
            if (binding.etUserName.text!!.isEmpty() && binding.etUserEmail.text!!.isEmpty() && binding.etUserMobile.text!!.isEmpty()) {
                Toast.makeText(this@RegistrationActivity, "Enter all the fields!", Toast.LENGTH_SHORT).show()
            } else {
                performUserRegistration()
            }
        }

    }

    private fun performUserRegistration() {

        val intent = Intent(this@RegistrationActivity, OTPActivity::class.java)
        intent.putExtra("name", binding.etUserName.text.toString())
        intent.putExtra("email", binding.etUserEmail.text.toString())
        intent.putExtra("mobile", binding.etUserMobile.text.toString())
        startActivity(intent)

        saveUserToDatabase()

    }

    private fun saveUserToDatabase() {
        
    }
}