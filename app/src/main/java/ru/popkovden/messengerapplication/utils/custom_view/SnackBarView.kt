package ru.popkovden.messengerapplication.utils.custom_view

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.popkovden.messengerapplication.R

class SnackBarView {

    fun showSnackBar(view: View, text: String, duration: Int) {
        val snackbar = Snackbar.make(view, text, duration)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.redColor))
        snackbar.setTextColor(ContextCompat.getColor(view.context, R.color.whiteColor))
        snackbar.show()
    }
}