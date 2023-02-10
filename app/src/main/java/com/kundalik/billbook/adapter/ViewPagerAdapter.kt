package com.kundalik.billbook.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager?,
    private val fragmentList: ArrayList<Fragment>,
) : FragmentPagerAdapter(fragmentManager!!) {

    companion object {
        val TAB_TITLE = arrayOf("Products", "Expense", "Tea")
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLE[position]
    }

}