package ru.popkovden.messengerapplication.utils.text_watcher_for_verify_code

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import ru.popkovden.messengerapplication.R

class RequestFocusToPreviousView {

    companion object {

        @BindingAdapter("app:requestFocusToPreviousView")
        @JvmStatic
        fun requestFocusToPreviousView(currentView: AppCompatEditText, previousView: AppCompatEditText?) {
            currentView.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.firstCodeBox && currentView.text!!.isEmpty()) {
                        previousView?.text = null
                        previousView?.requestFocus()
                        return true
                    }
                    return false
                }
            })
        }
    }
}