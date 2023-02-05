package com.kundalik.billbook.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
import java.lang.Integer.parseInt


class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding

    //fields variables
    private lateinit var CC: TextInputEditText //current cash expense
    private lateinit var HC: TextInputEditText //home cash expense
    private lateinit var BK: TextInputEditText //banking
    private lateinit var DT: TextInputEditText //shop cash expense
    private lateinit var DD: TextInputEditText //five percent
    private lateinit var CI: TextInputEditText //cash in

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

        return binding.root
    }



    private fun uploadProductDataToDatabase() = if (
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
        //dialog creation
        binding.btnContinue.setOnClickListener {
            //calculating total
            val p1 = parseInt(binding.etProductAmount1.text.toString())
            val p2 = parseInt(binding.etProductAmount2.text.toString())
            val p3 = parseInt(binding.etProductAmount3.text.toString())
            val p4 = parseInt(binding.etProductAmount4.text.toString())
            val p5 = parseInt(binding.etProductAmount5.text.toString())
            val p6 = parseInt(binding.etProductAmount6.text.toString())
            val p7 = parseInt(binding.etProductAmount7.text.toString())
            val p8 = parseInt(binding.etProductAmount8.text.toString())
            val p9 = parseInt(binding.etProductAmount9.text.toString())
            val p10 = parseInt(binding.etProductAmount10.text.toString())
            val p11 = parseInt(binding.etProductAmount11.text.toString())
            val p12 = parseInt(binding.etProductAmount12.text.toString())
            val p13 = parseInt(binding.etProductAmount13.text.toString())
            val p14 = parseInt(binding.etProductAmount14.text.toString())
            val p15 = parseInt(binding.etProductAmount15.text.toString())

            val totalAmount =
                (p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9 + p10 + p11 + p12 + p13 + p14 + p15)

            Toast.makeText(requireContext(), "$totalAmount", Toast.LENGTH_SHORT).show()

            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.item_expense_dialog)
            dialog.setCancelable(false)
            val dismissDialog = dialog.findViewById<Button>(R.id.btn_cancel_dialog)
            dismissDialog.setOnClickListener {
                dialog.dismiss()
            }
            //set product total amount to cc expense
            CC = dialog.findViewById(R.id.et_current_cash_expense)
            CC.setText(totalAmount.toString())
            //set home cash and banking
            HC = dialog.findViewById(R.id.et_home_cash_expense)
            BK = dialog.findViewById(R.id.et_online_banking)

            //set total amount
            val makeTotal = dialog.findViewById<Button>(R.id.btn_make_total)
            makeTotal.setOnClickListener {
                val CC = parseInt(CC.text.toString()) //current cash
                val BC =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_online_banking).text.toString())//banking cash
                val SC =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_shop_cash).text.toString()) //shop cash

                val dailyTotal = (CC + BC + SC)
                Toast.makeText(requireContext(), "daily total : $dailyTotal", Toast.LENGTH_SHORT)
                    .show()
                DT = dialog.findViewById(R.id.et_daily_total)
                DT.setText(dailyTotal.toString())
            }
            //set 5% percent
            val makeFivePer = dialog.findViewById<Button>(R.id.btn_make_five_per)
            makeFivePer.setOnClickListener {
                val dt = dialog.findViewById(R.id.et_daily_total) as TextInputEditText
                val DT = parseInt(dt.text.toString())
                val fivePercent = ((DT / 100) * 5)
                Toast.makeText(requireContext(), "daily total : $fivePercent", Toast.LENGTH_SHORT)
                    .show()
                DD = dialog.findViewById(R.id.et_five_per) //five percent
                DD.setText(fivePercent.toString())
            }
            //set cash in will became next home cash
            val makeCashIn = dialog.findViewById<Button>(R.id.btn_make_cash_in)
            makeCashIn.setOnClickListener {
                val SC =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_shop_cash).text.toString()) //shop cash
                val dt =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_five_per).text.toString()) //daily total
                val cashIn = (SC - dt)
                Toast.makeText(requireContext(), "daily total : $cashIn", Toast.LENGTH_SHORT).show()
                CI = dialog.findViewById(R.id.et_cash_in)
                CI.setText(cashIn.toString())
            }
            val saveDailyExpense = dialog.findViewById<Button>(R.id.btn_save_expense)
            dialog.show()

            saveDailyExpense.setOnClickListener {
                clearAllFields()
                dialog.dismiss()
            }
            //saveDateToDatabase()

        }

    } else {
        Toast.makeText(requireContext(), "Enter 00 in empty filed", Toast.LENGTH_SHORT).show()
    }

    private fun saveDateToDatabase() {

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

        Toast.makeText(requireContext(), "All filed clear Insert Data Again", Toast.LENGTH_SHORT)
            .show()
    }

}