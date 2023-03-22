package com.id22.core.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat

fun EditText.onSearch(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callback.invoke()
            return@setOnEditorActionListener true
        }
        false
    }
}

fun View.showSoftKeyboard() {
    post {
        if (this.requestFocus()) {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun View.hideSoftKeyboard() {
    post {
        if (this.requestFocus()) {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}

fun TextView.customTextColor(
    resColor: Int,
    fontSize: Float = 14f,
    isStrikeThru: Boolean? = false,
    isBold: Boolean? = false,
) {
    textSize = fontSize
    setTextColor(ContextCompat.getColor(context, resColor))

    paintFlags = if (isStrikeThru == true) {
        this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }

    if (isBold == true) {
        setTypeface(null, Typeface.BOLD)
    }
}