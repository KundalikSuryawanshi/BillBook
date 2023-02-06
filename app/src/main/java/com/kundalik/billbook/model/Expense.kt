package com.kundalik.billbook.model

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.kundalik.billbook.MainActivity
import com.kundalik.billbook.R

data class Expense(
    val currentCashExpense: TextInputEditText,
    val homeCashExpense:TextInputEditText,
    val onlineBanking:TextInputEditText,
    val shopCashExpense: TextInputEditText,
    val fivePercent: TextInputEditText,
    val cashIn: TextInputEditText
)


