package com.polok.eubmanagement.util

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


fun View?.hideSoftKeyBoard() = try {
    this?.post {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.apply {
            hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
} catch (e: Exception) {
    e.printStackTrace()
}

fun Context?.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View?.makeVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.makeInVisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.makeGone() {
    this?.visibility = View.GONE
}

fun Context.getDrawableCompat(@DrawableRes drawable: Int) = ContextCompat.getDrawable(
    this, drawable
)


fun Resources.getDrawableCompat(@DrawableRes drawable: Int) = ResourcesCompat.getDrawable(
    this, drawable, null
)


fun Resources.getColorCompat(@ColorRes color: Int) = ResourcesCompat.getColor(
    this, color, null
)

fun EditText?.showErrorOnUi(errorMessage: String?) {
    this?.error = errorMessage
    this?.requestFocus()
}

fun getCurrentDate(): String = try {
    SimpleDateFormat("dd MMMM, yyyy", Locale.US).format(
        Calendar.getInstance(TimeZone.getDefault()).time
    )
} catch (e: Exception) {
    e.printStackTrace()
    "N/A"
}