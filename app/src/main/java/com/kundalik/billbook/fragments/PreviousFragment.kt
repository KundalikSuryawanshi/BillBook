package com.kundalik.billbook.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.kundalik.billbook.R
import com.kundalik.billbook.adapter.ExpenseAdapter
import com.kundalik.billbook.databinding.FragmentPreviousBinding
import com.kundalik.billbook.model.Expense
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class PreviousFragment : Fragment() {

    private lateinit var binding: FragmentPreviousBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var expenseRecyclerView: RecyclerView
    private lateinit var expenseArrayList: ArrayList<Expense>

    val calendar = Calendar.getInstance().time
    val date = DateFormat.getDateInstance().format(calendar)
    val year = Calendar.YEAR.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPreviousBinding.inflate(layoutInflater)

        expenseRecyclerView = binding.rvPreviousExpense
        expenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        expenseRecyclerView.setHasFixedSize(true)

        expenseArrayList = arrayListOf<Expense>()
        loadExpenseData()

        return binding.root
    }

    private fun loadExpenseData() {

        val dialog2 = Dialog(requireContext())
        dialog2.setCancelable(false)
        dialog2.setContentView(R.layout.loading_layout)
        if (dialog2.window != null) {
            dialog2.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog2.show()

        databaseReference =
            FirebaseDatabase.getInstance().getReference("Expenses")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (expenseSnapshot in snapshot.children) {

                        val expense = expenseSnapshot.getValue<Expense>(Expense::class.java)
                        expenseArrayList.add(expense!!)
                    }
                    expenseRecyclerView.adapter = ExpenseAdapter(expenseArrayList)
                    dialog2.dismiss()
                } else {
                    Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
                    dialog2.dismiss()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error ${error.message}", Toast.LENGTH_LONG).show()
                dialog2.dismiss()
            }

        })

    }


}