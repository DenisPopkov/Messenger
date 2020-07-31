package ru.popkovden.messengerapplication.data.repository.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.utils.helper.readAllMessagesInChat

class ReadReceiverAction : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {

        CoroutineScope(IO).launch {
            val UID = intent.getStringExtra("UID")!!
            val userUID = intent.getStringExtra("userUID")!!
            val messageFromNotification = intent.getStringExtra("messageFromNotification")!!

            if (messageFromNotification == "Фото") {
                readAllMessagesInChat(UID, userUID, "receivedImages")
            } else {
                readAllMessagesInChat(UID, userUID, "receivedMessages")
            }
        }

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(FirebaseNotificationService.NOTIFICATION_ID)
    }
}