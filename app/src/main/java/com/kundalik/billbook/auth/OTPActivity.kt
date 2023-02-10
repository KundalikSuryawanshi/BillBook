package com.kundalik.billbook.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.ActivityOtpactivityBinding
import java.util.concurrent.TimeUnit


class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val userMobile = "+91" + intent.getStringExtra("mobile").toString()

        setCountDownTimer()

        performVerification(userMobile, auth)

        binding.tvResendOtp.setOnClickListener {
            performVerification(userMobile, auth)
        }

        binding.btnOtpCnt.setOnClickListener {
            verifyUserOtp(verificationId)
        }


    }

    private fun setCountDownTimer() {

        timer = object : CountDownTimer(6000, 10) {

            override fun onTick(millisUntilFinished: Long) {
                binding.etCountDown.text = millisUntilFinished.toString()
            }

            override fun onFinish() {
                binding.etCountDown.text = "Failed!!!!!"
            }

        }
    }


    private fun performVerification(userMobile: String, auth: FirebaseAuth) {

        val dialog = Dialog(this@OTPActivity)
        dialog.setContentView(R.layout.loading_layout)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.setCancelable(false)
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

        val dialog = Dialog(this@OTPActivity)
        dialog.setContentView(R.layout.loading_layout)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.setCancelable(false)
        dialog.show()


        if (binding.etUserOtp.text!!.isNotEmpty()) {
            val authCredential =
                PhoneAuthProvider.getCredential(verificationId, binding.etUserOtp.text!!.toString())
            auth.signInWithCredential(authCredential)
                .addOnSuccessListener {
                    startActivity(Intent(this@OTPActivity, RegistrationActivity::class.java))
                    dialog.dismiss()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this@OTPActivity, "Error ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
        } else {
            Toast.makeText(this@OTPActivity, "Enter OTP (one time password)", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }


}