package com.kundalik.billbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kundalik.billbook.R
import com.kundalik.billbook.model.Expense

class ExpenseAdapter(private val expenseList: ArrayList<Expense>): RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_expense_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = expenseList[position]

        holder.date.text = currentList.currentDate.toString()
        holder.currentCashExpense.text = currentList.currentCashExpense.toString()
        holder.homeCashExpense.text = currentList.homeCashExpense.toString()
        holder.onlineBanking.text = currentList.onlineBanking.toString()
        holder.shopCashExpense.text = currentList.shopCash.toString()
        holder.dailyTotal.text = currentList.dailyTotal.toString()
        holder.fivePercent.text = currentList.fivePercent.toString()
        holder.cashIn.text = currentList.cashIn.toString()
    }

    override fun getItemCount(): Int {
        return expenseList.size // 30 //last 30 days data will be available to watch user
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val date: TextView = itemView.findViewById(R.id.tv_rv_date)

        val currentCashExpense: TextView = itemView.findViewById(R.id.tv_current_cash_expese)
        val homeCashExpense: TextView = itemView.findViewById(R.id.tv_home_cash_expense)
        val onlineBanking: TextView = itemView.findViewById(R.id.tv_online_banking)
        val shopCashExpense: TextView = itemView.findViewById(R.id.tv_shop_cash)
        val dailyTotal: TextView = itemView.findViewById(R.id.tv_daily_total)
        val fivePercent: TextView = itemView.findViewById(R.id.tv_five_per)
        val cashIn: TextView = itemView.findViewById(R.id.tv_cash_in)

    }
}