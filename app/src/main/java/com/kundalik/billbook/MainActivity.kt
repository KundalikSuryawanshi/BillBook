package com.kundalik.billbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.kundalik.billbook.adapter.ViewPagerAdapter
import com.kundalik.billbook.auth.LoginActivity
import com.kundalik.billbook.auth.MobileNumberActivity
import com.kundalik.billbook.auth.RegistrationActivity
import com.kundalik.billbook.databinding.ActivityMainBinding
import com.kundalik.billbook.fragments.ExpenseFragment
import com.kundalik.billbook.fragments.OtherFragment
import com.kundalik.billbook.fragments.PreviousFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseMessaging: FirebaseMessaging
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkUserNullOrNot()

        setUpViewPagerAdapter()

        getFCMToken()

    }

    private fun checkUserNullOrNot() {
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            startActivity(Intent(this@MainActivity, MobileNumberActivity::class.java))
            finish()
        }
    }

    private fun setUpViewPagerAdapter() {
        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(ExpenseFragment())
        fragmentArrayList.add(PreviousFragment())
        fragmentArrayList.add(OtherFragment())

        val adapter = ViewPagerAdapter(this@MainActivity, supportFragmentManager, fragmentArrayList)
        binding.viewPager.adapter = adapter
        binding.tbLayout.setupWithViewPager(binding.viewPager)

    }
    
    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                Toast.makeText(this, "Failed to get FCM token", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
            //get new fcm token
            val token = it.result
            Log.d("FCMTOKEN", token)
        }
        
    }
}