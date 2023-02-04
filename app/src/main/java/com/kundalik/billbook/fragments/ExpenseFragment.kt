package com.kundalik.billbook.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.item_expense_dialog)
            dialog.setCancelable(false)
            val makeTotal = dialog.findViewById<Button>(R.id.btn_make_total)
            val saveProductExpense = dialog.findViewById<Button>(R.id.btn_save_expense)

            makeTotal.setOnClickListener {
                //calculating total
                val p1 = Integer.parseInt(binding.etProduct1.text.toString())
                val p2 = Integer.parseInt(binding.etProduct2.text.toString())
                val p3 = Integer.parseInt(binding.etProduct3.text.toString())
                val p4 = Integer.parseInt(binding.etProduct4.text.toString())
                val p5 = Integer.parseInt(binding.etProduct5.text.toString())
                val p6 = Integer.parseInt(binding.etProduct6.text.toString())
                val p7 = Integer.parseInt(binding.etProduct7.text.toString())
                val p8 = Integer.parseInt(binding.etProduct8.text.toString())
                val p9 = Integer.parseInt(binding.etProduct9.text.toString())
                val p10 = Integer.parseInt(binding.etProduct10.text.toString())
                val p11 = Integer.parseInt(binding.etProduct11.text.toString())
                val p12 = Integer.parseInt(binding.etProduct12.text.toString())
                val p13 = Integer.parseInt(binding.etProduct13.text.toString())
                val p14 = Integer.parseInt(binding.etProduct14.text.toString())
                val p15 = Integer.parseInt(binding.etProduct15.text.toString())

               val totalAmount =(p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13 + p14 + p15)

                dialog.findViewById<TextInputEditText>(R.id.et_product_expense).setText(totalAmount)

                saveProductExpense.setOnClickListener {
                    Toast.makeText(requireContext(), "${totalAmount}", Toast.LENGTH_SHORT).show()
                    uploadProductDataToDatabase()
                }
            }


            dialog.show()
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
            Toast.makeText(
                requireContext(),
                "data ready to insert ${binding.etProduct1.text} /n ${binding.etProductAmount1.text.toString()}",
                Toast.LENGTH_SHORT
            ).show()

        } else {
            Toast.makeText(requireContext(), "Enter 00 in empty filed", Toast.LENGTH_SHORT).show()
        }
    }

}