package com.kundalik.billbook.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.kundalik.billbook.MainActivity
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.ActivityOtpactivityBinding
import java.util.concurrent.TimeUnit


class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserData()

        binding.btnOtpCnt.setOnClickListener {
            verifyUserOtp(verificationId)
        }


    }

    private fun getUserData() {
        val userName = intent.getStringExtra("name").toString()
        val userEmail = intent.getStringExtra("email").toString()
        val userMobile = "+91" + intent.getStringExtra("mobile").toString()
        auth = FirebaseAuth.getInstance()

        performVerification(userMobile, auth)

    }


    private fun performVerification(userMobile: String, auth: FirebaseAuth) {

        val builder = AlertDialog.Builder(this)

        builder.setMessage("Please wait.......")
        builder.setTitle("Loading")
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

        val authOptions = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(userMobile)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@OTPActivity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    dialog.dismiss()
                    verificationId = p0
                }

                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    dialog.dismiss()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(
                        this@OTPActivity,
                        "Please try Again ${p0.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }

            }).build()
        PhoneAuthProvider.verifyPhoneNumber(authOptions)

    }

    private fun verifyUserOtp(verificationId: String) {
        if (binding.etUserOtp.text!!.isNotEmpty()) {
            dialog.show()
            val authCredential =
                PhoneAuthProvider.getCredential(verificationId, binding.etUserOtp.text!!.toString())
            auth.signInWithCredential(authCredential)
                .addOnCompleteListener {
                    startActivity(Intent(this@OTPActivity, MainActivity::class.java))
                    dialog.dismiss()
                    finish()
                }
        } else {
            Toast.makeText(this@OTPActivity, "Enter OTP (one time password)", Toast.LENGTH_SHORT)
                .show()
        }
    }

}