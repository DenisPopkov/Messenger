package ru.popkovden.messengerapplication.data.repository.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.ui.fragment.ChatScreenFragment
import ru.popkovden.messengerapplication.utils.helper.DecodeUri
import ru.popkovden.messengerapplication.utils.helper.getCircleBitmap
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class FirebaseNotificationService : FirebaseMessagingService() {

    private val channelId = "channelId"

    companion object {
        var sharedPreferences: SharedPreferences? = null

        var token: String?
            get() = sharedPreferences?.getString("token", "")
            set(value) {
                sharedPreferences?.edit()?.putString("token", value)?.apply()
            }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        token = newToken
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotificationChannel()

        val intent = Intent(this, ChatScreenFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0,
            intent, FLAG_ONE_SHOT)

        val largeIcon = getCircleBitmap(DecodeUri.decodeUriToBitmap(
            Uri.parse(InfoAboutUser.userProfileImageFile), this))

        val scaledLargeIcon = Bitmap.createScaledBitmap(largeIcon, 128, 128, false)

        val notification =
            NotificationCompat.Builder(this, channelId)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.mini_notification_icon)
                .setAutoCancel(true)
                .setLargeIcon(scaledLargeIcon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(resources.getColor(R.color.mainColor))
                .addAction(createReadAction())
                .addAction(createReplyAction())

        with(NotificationManagerCompat.from(this)) {
            notify(0, notification.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "Сообщения"
            val desc = "Сообщения полученные из диалогов"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = desc
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createReadAction(): NotificationCompat.Action {

        val intent = Intent(this, ChatScreenFragment::class.java)
        intent.putExtra("wasRead", "read")
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val action = NotificationCompat.Action.Builder(R.drawable.ic_launcher_background, "Прочитано", pendingIntent)

        return action.build()
    }

    private fun createReplyAction(): NotificationCompat.Action {

        val intent = Intent(this, ChatScreenFragment::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val remoteInput = RemoteInput.Builder("key_reply").setLabel("Ответить").build()
        val action = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send, "Ответить", pendingIntent)

        return action.addRemoteInput(remoteInput).build()
    }
}