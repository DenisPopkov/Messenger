package ru.popkovden.messengerapplication.utils.textWatcher

import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import ru.popkovden.messengerapplication.R

class MessageInput {

    companion object {

        @BindingAdapter("app:messageInputChecker")
        @JvmStatic
        fun messageInputChecker(currentView: AppCompatEditText, imageButton: ImageButton) {
            currentView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(message: Editable?) {

                    when (!message.toString().isBlank()) {
                        true -> imageButton.setImageResource(R.drawable.send_icon)
                        false -> imageButton.setImageResource(R.drawable.microphone_icon)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }
}