package ru.popkovden.messengerapplication.utils.custom_view

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.popkovden.messengerapplication.R

object SnackBarView {

    fun showSnackBar(view: View, text: String, duration: Int) {
        val snackBar = Snackbar.make(view, text, duration)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.redColor))
        snackBar.setTextColor(ContextCompat.getColor(view.context, R.color.whiteColor))
        snackBar.show()
    }
}