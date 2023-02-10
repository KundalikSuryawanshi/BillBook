package com.kundalik.billbook.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.ActivityMobileNumberBinding

class MobileNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMobileNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMobileContinue.setOnClickListener {
            val userMobile = binding.etUserMobile.text.toString()
            if (userMobile.isEmpty()) {
                Toast.makeText(
                    this@MobileNumberActivity,
                    "Enter your mobile number",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this@MobileNumberActivity, OTPActivity::class.java)
                intent.putExtra("mobile", userMobile)
                startActivity(intent)
                finish()
            }

        }
    }
}