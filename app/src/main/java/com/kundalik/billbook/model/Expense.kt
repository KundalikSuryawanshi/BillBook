package com.kundalik.billbook.model

import com.google.android.material.textfield.TextInputEditText
import java.util.Date

data class Expense(

    val currentDate: String="",
    val currentCashExpense: Int = 0,
    val homeCashExpense: Int = 0,
    val onlineBanking: Int = 0,
    val shopCash: Int = 0,
    val dailyTotal: Int = 0,
    val fivePercent: Int = 0,
    val cashIn: Int = 0
)


