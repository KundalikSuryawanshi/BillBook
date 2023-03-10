package com.kundalik.billbook.fragments

import android.app.Dialog
import android.app.Service
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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


        //to check internet connection
        checkInternetConnection()

        expenseRecyclerView = binding.rvPreviousExpense
        expenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        expenseRecyclerView.setHasFixedSize(true)


        expenseArrayList = arrayListOf<Expense>()
        loadExpenseData()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchExpenseByDate(newText)
                return true
            }

        })

        return binding.root
    }

    fun searchExpenseByDate(text: String) {
        val searchList = kotlin.collections.ArrayList<Expense>()
        for (date in searchList) {
            if (date.currentDate?.lowercase()?.contains(text.lowercase()) == true) {
                searchList.add(date)
            }
        }
        val exp = ExpenseAdapter(expenseArrayList)
        exp.searchByDate(searchList)
    }

    private fun checkInternetConnection() {

        var connectivity: ConnectivityManager? = null
        var info: NetworkInfo? = null

        connectivity =
            requireActivity().getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        info = connectivity.activeNetworkInfo
        if (info != null) {
            if (info.state == NetworkInfo.State.CONNECTED) {
                //Toast.makeText(requireContext(), "connected", Toast.LENGTH_SHORT).show()
            } else if (info.state == NetworkInfo.State.SUSPENDED) {
                Toast.makeText(requireContext(), "No Internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loadExpenseData() {

        val dialog2 = Dialog(requireContext())
        //dialog2.setCancelable(false)
        dialog2.setTitle("Waiting for Internet Connection")
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