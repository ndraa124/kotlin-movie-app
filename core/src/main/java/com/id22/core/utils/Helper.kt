package com.id22.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun showLog(tag: String, string: String) {
    val maxLogSize = 1000
    string.chunked(maxLogSize).forEach { Log.v(tag, it) }
}

fun setHideKeyboard(view: View, activity: Activity) {
    val manager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(view.windowToken, 0)
}

@SuppressLint("SimpleDateFormat")
fun getDateFormat(date: String): String {
    val localeID = Locale("in", "ID")
    val defaultFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", localeID)
    defaultFormat.timeZone = TimeZone.getTimeZone("GMT")

    try {
        return SimpleDateFormat("MMMM dd, yyyy").format(defaultFormat.parse(date)!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}