package com.kundalik.billbook.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kundalik.billbook.MainActivity
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAlreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
            finish()
        }

//        binding.btnLoginContinue.setOnClickListener {
//            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            finish()
//        }

    }
}