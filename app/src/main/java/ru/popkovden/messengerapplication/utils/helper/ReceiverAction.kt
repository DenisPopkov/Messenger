package ru.popkovden.messengerapplication.utils.helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceiverAction : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent!!.getStringExtra("wasRead")
        Toast.makeText(context, action, Toast.LENGTH_SHORT).show()
    }
}