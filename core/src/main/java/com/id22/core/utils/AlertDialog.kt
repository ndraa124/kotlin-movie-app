package com.id22.core.utils

import android.app.Activity
import android.graphics.Color
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun showSnackBarAlert(activity: Activity, txt: String, length: String? = null) {
    val snackBar: Snackbar = when (length) {
        LENGTH_LONG -> {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                txt,
                Snackbar.LENGTH_LONG
            )
        }
        LENGTH_INDEFINITE -> {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                txt,
                Snackbar.LENGTH_INDEFINITE
            )
        }
        else -> {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                txt,
                Snackbar.LENGTH_SHORT
            )
        }
    }

    snackBar.setActionTextColor(Color.parseColor("#FFFFFF"))
    snackBar.setAction("X") {
        snackBar.dismiss()
    }

    snackBar.show()
}

fun showToastAlert(activity: Activity, txt: String, length: String? = null) {
    if (length == LENGTH_LONG) {
        Toast.makeText(activity, txt, Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(activity, txt, Toast.LENGTH_SHORT).show()
    }
}
