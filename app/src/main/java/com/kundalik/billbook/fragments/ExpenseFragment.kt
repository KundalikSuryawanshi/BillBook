package com.kundalik.billbook.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.kundalik.billbook.R
import com.kundalik.billbook.R.id.btn_save_expense
import com.kundalik.billbook.databinding.FragmentExpenseBinding
import com.kundalik.billbook.model.Expense
import java.lang.Integer.parseInt
import java.text.DateFormat
import java.util.Calendar


class ExpenseFragment : Fragment() {

    private lateinit var binding: FragmentExpenseBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var firestore: FirebaseFirestore

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
        database = FirebaseDatabase.getInstance()
        firestore = FirebaseFirestore.getInstance()


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


        binding.btnSaveProduct.setOnClickListener {
            if (
                binding.etProduct1.text.isNotEmpty() &&
                binding.etProduct2.text.isNotEmpty() &&
                binding.etProduct3.text.isNotEmpty() &&
                binding.etProduct4.text.isNotEmpty() &&
                binding.etProduct5.text.isNotEmpty() &&
                binding.etProduct6.text.isNotEmpty() &&
                binding.etProduct7.text.isNotEmpty() &&
                binding.etProduct8.text.isNotEmpty() &&
                binding.etProduct9.text.isNotEmpty() &&
                binding.etProduct10.text.isNotEmpty() &&
                binding.etProduct11.text.isNotEmpty() &&
                binding.etProduct12.text.isNotEmpty() &&
                binding.etProduct13.text.isNotEmpty() &&
                binding.etProduct14.text.isNotEmpty() &&
                binding.etProduct15.text.isNotEmpty()
            ) {
                uploadProductDataToFireStore()
            } else {
                Toast.makeText(requireContext(), "fill other filed with null", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        binding.btnContinue.setOnClickListener {
            makeCalculations()
        }

        return binding.root
    }

    private fun uploadProductDataToFireStore() {

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

        //upload product details to database
        //logic to save data
        val calendar = Calendar.getInstance().time
        val date = DateFormat.getDateInstance().format(calendar)
        val year = Calendar.YEAR.toString()

        //loading
        val dialog2 = Dialog(requireContext())
        dialog2.setContentView(R.layout.loading_layout)
        if (dialog2.window != null) {
            dialog2.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog2.show()

        //products
        val productMap = mutableMapOf<String, Int>()

        productMap.put(binding.etProduct1.text!!.toString(), p1)
        productMap.put(binding.etProduct2.text!!.toString(), p2)
        productMap.put(binding.etProduct3.text!!.toString(), p3)
        productMap.put(binding.etProduct4.text!!.toString(), p4)
        productMap.put(binding.etProduct5.text!!.toString(), p5)
        productMap.put(binding.etProduct6.text!!.toString(), p6)
        productMap.put(binding.etProduct7.text!!.toString(), p7)
        productMap.put(binding.etProduct8.text!!.toString(), p8)
        productMap.put(binding.etProduct9.text!!.toString(), p9)
        productMap.put(binding.etProduct10.text!!.toString(), p10)
        productMap.put(binding.etProduct11.text!!.toString(), p11)
        productMap.put(binding.etProduct12.text!!.toString(), p12)
        productMap.put(binding.etProduct13.text!!.toString(), p13)
        productMap.put(binding.etProduct14.text!!.toString(), p14)
        productMap.put(binding.etProduct15.text!!.toString(), p15)



        firestore.collection(year).document(date)
            .set(productMap)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                dialog2.dismiss()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "fail ${it.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                dialog2.dismiss()
            }
    }

    private fun makeCalculations() = if (
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


        binding.btnContinue.setOnClickListener {

            val dialog =
                LayoutInflater.from(requireContext()).inflate(R.layout.item_expense_dialog, null)
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(dialog)
            val expdialog = mBuilder.show()


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

            dialog.findViewById<Button>(R.id.btn_cancel_dialog).setOnClickListener {
                expdialog.dismiss()
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

                if (CC != null && BC != null && SC != null) {
                    val dailyTotal = (CC + BC + SC)
                    dialog.findViewById<TextInputEditText>(R.id.et_daily_total)
                        .setText(dailyTotal.toString())
                } else {
                    Toast.makeText(requireContext(), "Enter all the value", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            //set 5% percent
            val makeFivePer = dialog.findViewById<Button>(R.id.btn_make_five_per)
            makeFivePer.setOnClickListener {
                val dt = dialog.findViewById(R.id.et_daily_total) as TextInputEditText
                val DT = parseInt(dt.text.toString())
                if (DT != null) {
                    val fivePercent = ((DT / 100) * 5)
                    DD = dialog.findViewById(R.id.et_five_per) //five percent
                    DD.setText(fivePercent.toString())
                } else {
                    Toast.makeText(requireContext(), "Enter all the value", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            //set cash in will became next home cash
            val makeCashIn = dialog.findViewById<Button>(R.id.btn_make_cash_in)
            makeCashIn.setOnClickListener {
                val SC =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_shop_cash).text.toString()) //shop cash
                val dt =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_five_per).text.toString()) //daily total
                if (SC != null) {
                    val cashIn = (SC - dt)
                    CI = dialog.findViewById(R.id.et_cash_in)
                    CI.setText(cashIn.toString())
                } else {
                    Toast.makeText(requireContext(), "Enter all the value", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            //save Expense
            val saveDailyExpense = dialog.findViewById<Button>(btn_save_expense)

            saveDailyExpense.setOnClickListener {

                val dialog2 = Dialog(requireContext())
                dialog2.setCancelable(false)
                dialog2.setContentView(R.layout.loading_layout)
                if (dialog2.window != null) {
                    dialog2.window!!.setBackgroundDrawable(ColorDrawable(0))
                }
                dialog2.show()

                val calender = Calendar.getInstance().time
                val date = DateFormat.getDateInstance().format(calender)
                val year = Calendar.YEAR.toString()

                //val currentDate = date

                val currentCash =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_current_cash_expense).text.toString())

                val homeCash =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_home_cash_expense).text.toString())

                val onlineBanking =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_online_banking).text.toString())

                val shopCash =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_shop_cash).text.toString())

                val dailyTotal =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_daily_total).text.toString())

                val fivePercent =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_five_per).text.toString())

                val cashIn =
                    parseInt(dialog.findViewById<TextInputEditText>(R.id.et_cash_in).text.toString())


                val expense =
                    Expense(
                        //currentDate,
                        currentCash,
                        homeCash,
                        onlineBanking,
                        dailyTotal,
                        shopCash,
                        fivePercent,
                        cashIn
                    )

                if (currentCash != null && homeCash != null && onlineBanking != null && dailyTotal != null && shopCash != null && fivePercent != null && cashIn != null) {

                    database.reference.child("Expenses").child(date)
                        .setValue(expense)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                            dialog2.dismiss()
                            expdialog.dismiss()
                            clearAllFields()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "Error ${it.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            dialog2.dismiss()
                        }
                } else {
                    Toast.makeText(requireContext(), "Enter All the fields", Toast.LENGTH_SHORT)
                        .show()
                }
                expdialog.show()
            }

        }

    } else {
        Toast.makeText(requireContext(), "Enter 00 in empty filed", Toast.LENGTH_SHORT).show()
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

        binding.etProduct1.setText("")
        binding.etProduct2.setText("")
        binding.etProduct3.setText("")
        binding.etProduct4.setText("")
        binding.etProduct5.setText("")
        binding.etProduct6.setText("")
        binding.etProduct7.setText("")
        binding.etProduct8.setText("")
        binding.etProduct9.setText("")
        binding.etProduct9.setText("")
        binding.etProduct10.setText("")
        binding.etProduct11.setText("")
        binding.etProduct12.setText("")
        binding.etProduct13.setText("")
        binding.etProduct14.setText("")
        binding.etProduct15.setText("")


        Toast.makeText(requireContext(), "All filed clear Insert Data Again", Toast.LENGTH_SHORT)
            .show()
    }

}
