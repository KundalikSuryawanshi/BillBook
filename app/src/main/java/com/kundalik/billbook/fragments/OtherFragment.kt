package com.kundalik.billbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.kundalik.billbook.databinding.FragmentOtherBinding
import java.lang.Integer.parseInt


class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding
    private lateinit var totalTea: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtherBinding.inflate(layoutInflater)

        binding.btnTotalTea.setOnClickListener {
            if (binding.etTotalTea.text!!.isNotEmpty()) {
                val totalTea = parseInt(binding.etTotalTea.text.toString())
                val total = totalTea * 7
                val teaExp = (total*25)/100
                val profit = total - teaExp
                binding.tvTeaProfit.text = profit.toString()
                binding.etTotalTea.setText("")
            }  else {
                Toast.makeText(requireContext(), "Enter Total Number of Tea", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }

}