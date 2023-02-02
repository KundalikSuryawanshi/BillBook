package com.kundalik.billbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.FragmentExpenseBinding


class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExpenseBinding.inflate(layoutInflater)

        val product = resources.getStringArray(R.array.products)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdownitem, product)
        binding.etProduct1.setAdapter(arrayAdapter)
        binding.etProduct2.setAdapter(arrayAdapter)
        binding.etProduct3.setAdapter(arrayAdapter)
        binding.etProduct4.setAdapter(arrayAdapter)
        binding.etProduct5.setAdapter(arrayAdapter)
        binding.etProduct6.setAdapter(arrayAdapter)
        binding.etProduct7.setAdapter(arrayAdapter)
        binding.etProduct8.setAdapter(arrayAdapter)
        binding.etProduct9.setAdapter(arrayAdapter)
        binding.etProduct10.setAdapter(arrayAdapter)
        binding.etProduct11.setAdapter(arrayAdapter)
        binding.etProduct12.setAdapter(arrayAdapter)
        binding.etProduct13.setAdapter(arrayAdapter)
        binding.etProduct14.setAdapter(arrayAdapter)
        binding.etProduct15.setAdapter(arrayAdapter)


        binding.btnContinue.setOnClickListener {

            uploadProductDataToDatabase()
        }
        binding.btnCancle.setOnClickListener {
            
            clearAllFields()
        }



        return binding.root
    }

    private fun clearAllFields() {

        binding.etProductAmount1.setText("")
        binding.etProductAmount2.setText("")
        binding.etProductAmount3.setText("")
        binding.etProductAmount4.setText("")
        binding.etProductAmount5.setText("")
        binding.etProductAmount6.setText("")
        binding.etProductAmount7.setText("")
        binding.etProductAmount8.setText("")
        binding.etProductAmount9.setText("")
        binding.etProductAmount10.setText("")
        binding.etProductAmount11.setText("")
        binding.etProductAmount12.setText("")
        binding.etProductAmount13.setText("")
        binding.etProductAmount14.setText("")
        binding.etProductAmount15.setText("")

        Toast.makeText(requireContext(), "All filed clear \n Insert Data Again", Toast.LENGTH_SHORT)
            .show()
    }

    private fun uploadProductDataToDatabase() {
        if (
            binding.etProductAmount1.text!!.isNotEmpty() &&
            binding.etProductAmount2.text!!.isNotEmpty() &&
            binding.etProductAmount3.text!!.isNotEmpty() &&
            binding.etProductAmount4.text!!.isNotEmpty() &&
            binding.etProductAmount5.text!!.isNotEmpty() &&
            binding.etProductAmount6.text!!.isNotEmpty() &&
            binding.etProductAmount7.text!!.isNotEmpty() &&
            binding.etProductAmount8.text!!.isNotEmpty() &&
            binding.etProductAmount9.text!!.isNotEmpty() &&
            binding.etProductAmount10.text!!.isNotEmpty() &&
            binding.etProductAmount11.text!!.isNotEmpty() &&
            binding.etProductAmount12.text!!.isNotEmpty() &&
            binding.etProductAmount13.text!!.isNotEmpty() &&
            binding.etProductAmount14.text!!.isNotEmpty() &&
            binding.etProductAmount15.text!!.isNotEmpty()

        ) {
            Toast.makeText(requireContext(), "data ready to insert ${binding.etProduct1.text} /n ${binding.etProductAmount1.text.toString()}", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(requireContext(), "Enter 00 in empty filed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}