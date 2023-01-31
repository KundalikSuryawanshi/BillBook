package com.kundalik.billbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kundalik.billbook.adapter.ViewPagerAdapter
import com.kundalik.billbook.auth.LoginActivity
import com.kundalik.billbook.auth.RegistrationActivity
import com.kundalik.billbook.databinding.ActivityMainBinding
import com.kundalik.billbook.fragments.ExpenseFragment
import com.kundalik.billbook.fragments.OtherFragment
import com.kundalik.billbook.fragments.PreviousFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkUserNullOrNot()

        setUpViewPagerAdapter()

    }

    private fun checkUserNullOrNot() {
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            startActivity(Intent(this@MainActivity, RegistrationActivity::class.java))
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
}