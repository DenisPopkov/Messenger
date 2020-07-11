package ru.popkovden.messengerapplication.utils.textWatcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.databinding.BindingAdapter

class SentMessageWatcher {

    companion object {

        @BindingAdapter("app:sentMessageWatcher")
        @JvmStatic
        fun sentMessageWatcher(currentView: TextView, imageButton: Int) {
            currentView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(message: Editable?) {

                    when (!message.toString().isBlank()) {

                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}