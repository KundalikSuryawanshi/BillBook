package com.kundalik.billbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.FragmentPreviousBinding

class PreviousFragment : Fragment() {

    private lateinit var binding: FragmentPreviousBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPreviousBinding.inflate(layoutInflater)

        return binding.root
    }


}