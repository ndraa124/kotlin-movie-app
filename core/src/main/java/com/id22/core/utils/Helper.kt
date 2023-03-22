package com.id22.core.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

fun showLog(tag: String, string: String) {
    val maxLogSize = 1000
    string.chunked(maxLogSize).forEach { Log.v(tag, it) }
}

fun setHideKeyboard(view: View, activity: Activity) {
    val manager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(view.windowToken, 0)
}
