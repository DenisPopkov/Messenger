package ru.popkovden.messengerapplication.utils.text_watcher_for_verify_code

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.BindingAdapter
import ru.popkovden.messengerapplication.R

class RequestFocusToNextView internal constructor(private val currentView: View, private val nextView: View?) :
    TextWatcher {

    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (currentView.id) {
            R.id.firstCodeBox -> if (text.length == 1) nextView?.requestFocus()
            R.id.secondCodeBox -> if (text.length == 1) nextView?.requestFocus()
            R.id.thirdCodeBox -> if (text.length == 1) nextView?.requestFocus()
            R.id.fourthCodeBox -> if (text.length == 1) nextView?.requestFocus()
            R.id.fifthCodeBox -> if (text.length == 1) nextView?.requestFocus()
        }
    }

    override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
    override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
}