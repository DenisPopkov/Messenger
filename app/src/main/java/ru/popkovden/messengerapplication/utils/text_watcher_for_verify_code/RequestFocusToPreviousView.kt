package ru.popkovden.messengerapplication.utils.text_watcher_for_verify_code

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import ru.popkovden.messengerapplication.R

class RequestFocusToPreviousView internal constructor(private val currentView: AppCompatEditText, private val previousView: AppCompatEditText?) : View.OnKeyListener{
    override fun onKey(p0: View, keyCode: Int, event: KeyEvent): Boolean {
        if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.firstCodeBox && currentView.text!!.isEmpty()) {
            previousView?.text = null
            previousView?.requestFocus()
            return true
        }
        return false
    }
}