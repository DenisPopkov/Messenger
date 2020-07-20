package ru.popkovden.messengerapplication.data.repository.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.utils.helper.getCircleBitmap
import java.net.URL

class FirebaseNotificationService : FirebaseMessagingService() {

    companion object {
        var sharedPreferences: SharedPreferences? = null

        var token: String?
            get() = sharedPreferences?.getString("token", "")
            set(value) {
                sharedPreferences?.edit()?.putString("token", value)?.apply()
            }

        const val CHANNEL_ID = "ru.popkovden.messengerapplication"
        const val NOTIFICATION_ID = 1010
        const val REPLY_KEY = "reply_action"
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        token = newToken
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotificationChannel()

        // Получает фото отправителя
        val url = URL(message.data["image"].toString())
        val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        val largeIcon = getCircleBitmap(image)
        val scaledLargeIcon = Bitmap.createScaledBitmap(largeIcon, 128, 128, false)

        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setSmallIcon(R.drawable.mini_notification_icon)
                .setAutoCancel(true)
                .setLargeIcon(scaledLargeIcon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(resources.getColor(R.color.mainColor))
                .addAction(readAction())
                .addAction(replyAction())
                .setGroup("Messages")
                .setGroupSummary(true)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, notification.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "Сообщения"
            val desc = "Сообщения полученные из диалогов"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = desc
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun readAction(): NotificationCompat.Action {

        // Создает интент для действия прочитать
        val readIntent = Intent(applicationContext, ReadReceiverAction::class.java)
        readIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val readPendingIntent = PendingIntent.getBroadcast(applicationContext, 0,
            readIntent, FLAG_ONE_SHOT)

        return NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
            "Прочитать", readPendingIntent).build()
    }

    private fun replyAction(): NotificationCompat.Action {

        // Создает интент для действия ответа
        val replyIntent = Intent(applicationContext, ReplyReceiverAction::class.java)
        replyIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0,
            replyIntent, FLAG_ONE_SHOT)

        // Создает действие ответа
        val remoteInput = RemoteInput.Builder(REPLY_KEY).setLabel("Ответить").build()
        val action = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send, "Ответить", pendingIntent)

        return action.addRemoteInput(remoteInput).build()
    }
}