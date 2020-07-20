package ru.popkovden.messengerapplication.data.repository.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReadReceiverAction : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // TODO() СДЕЛАТЬ ПРОЧИТАНО СООБЩЕНИЕ ИЛИ НЕТ В ПРИЛОЖЕНИИ
        val remoteInput = intent?.getStringExtra("read")
        Toast.makeText(context, "Сообщения прочитано, но это пока не работает", Toast.LENGTH_SHORT).show()

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(FirebaseNotificationService.NOTIFICATION_ID)
    }
}