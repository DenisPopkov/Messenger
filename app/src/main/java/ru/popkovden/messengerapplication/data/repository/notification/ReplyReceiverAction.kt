package ru.popkovden.messengerapplication.data.repository.notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.popkovden.messengerapplication.data.repository.messages.SendMessageToUser
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.ui.fragment.FragmentMessengerScreen
import ru.popkovden.messengerapplication.utils.helper.getData.getCurrentDateTime
import ru.popkovden.messengerapplication.utils.helper.getData.toString
import ru.popkovden.messengerapplication.utils.helper.getData.updateCollectionSize
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class ReplyReceiverAction : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val currentTime = getCurrentDateTime().toString("HH:mm")
        val UID = InfoAboutUser.UID
        val userUID =  FragmentMessengerScreen.userUID
        val id = FragmentMessengerScreen.collectionSize

        val remoteInput: Bundle = RemoteInput.getResultsFromIntent(intent)
        val receiverText = remoteInput.getCharSequence(FirebaseNotificationService.REPLY_KEY).toString()

        SendMessageToUser.sendMessage(UID, userUID, SendMessageModel(receiverText, currentTime, UID, id, 0))
        updateCollectionSize(UID, id, userUID)

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(FirebaseNotificationService.NOTIFICATION_ID)
    }
}