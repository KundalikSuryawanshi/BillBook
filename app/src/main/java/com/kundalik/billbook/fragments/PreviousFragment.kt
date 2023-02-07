package com.kundalik.billbook.fragments

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
import java.util.*
import kotlin.collections.ArrayList

class PreviousFragment : Fragment() {

    private lateinit var binding: FragmentPreviousBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var expenseRecyclerView: RecyclerView
    private lateinit var expenseArrayList: ArrayList<Expense>

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR).toString()
    val month = c.get(Calendar.MONTH).toString()
    val day = c.get(Calendar.DAY_OF_MONTH).toString()
    val date = c.get(Calendar.DATE).toString()

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
        databaseReference =
            FirebaseDatabase.getInstance().getReference("Expenses").child(year)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (expenseSnapshot in snapshot.children) {

                        val expense = expenseSnapshot.getValue<Expense>(Expense::class.java)
                        expenseArrayList.add(expense!!)
                    }
                    expenseRecyclerView.adapter = ExpenseAdapter(expenseArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Error ${error.message}", Toast.LENGTH_LONG).show()
            }

        })

    }


}