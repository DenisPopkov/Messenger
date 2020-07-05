package ru.popkovden.messengerapplication.utils.textWatcher

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import ru.popkovden.messengerapplication.R

class PhoneInput {

    companion object {

        @BindingAdapter("app:phoneInputChecker")
        @JvmStatic
        fun phoneInputChecker(currentView: AppCompatEditText, statusView: AppCompatImageView) {
            currentView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(message: Editable?) {

                    if (!message.toString().isBlank()) {

                        statusView.visibility = View.VISIBLE

                        if (message.toString().contains("+") && message.toString().length >= 6 && !message.toString().isBlank()) {
                            statusView.setImageResource(R.drawable.success)
                        } else {
                            statusView.setImageResource(R.drawable.error_icon)
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}