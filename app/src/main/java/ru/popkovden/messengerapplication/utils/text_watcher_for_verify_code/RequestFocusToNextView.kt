package ru.popkovden.messengerapplication.utils.text_watcher_for_verify_code

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import ru.popkovden.messengerapplication.R

class RequestFocusToNextView {

    companion object {
        @BindingAdapter("app:requestFocusToNextView")
        @JvmStatic
        fun requestFocusToNextView(currentView: AppCompatEditText, nextView: AppCompatEditText?) {
            currentView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    when (currentView.id) {
                        R.id.firstCodeBox -> if (text.length == 1) nextView?.requestFocus()
                        R.id.secondCodeBox -> if (text.length == 1) nextView?.requestFocus()
                        R.id.thirdCodeBox -> if (text.length == 1) nextView?.requestFocus()
                        R.id.fourthCodeBox -> if (text.length == 1) nextView?.requestFocus()
                        R.id.fifthCodeBox -> if (text.length == 1) nextView?.requestFocus()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}