package ru.popkovden.messengerapplication.utils.helper

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.model.LastMessageModel
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.model.SentImageModel
import ru.popkovden.messengerapplication.utils.helper.getData.setLastMessage

suspend fun readAllMessagesInChat(UID: String, userUID: String, reference: String) = CoroutineScope(IO).launch {

    val updateMessageInfo = hashMapOf<String, Any>()

    val wasReadList = FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("chats").document(userUID)
        .collection(reference).get().await()

    val lastMessage = FirebaseFirestore.getInstance().collection("users").document(UID).collection("contacts")
        .document(userUID).collection("lastMessage").document("last").get().await()

    val lastMessageModel = lastMessage.toObject(LastMessageModel::class.java)!!
    setLastMessage(UID, userUID, lastMessageModel.lastMessage, lastMessageModel.timeSend, lastMessageModel.daySend, "true")

    if (reference == "sentImages" || reference == "receivedImages") {
        val result = wasReadList.toObjects(SentImageModel::class.java)

        for (readMessages in result) {
            updateMessageInfo["wasRead"] = "true"
            Log.d("efefe", "always ok - ${readMessages.documentId}")

            try {
                FirebaseFirestore.getInstance().collection("users").document(UID)
                    .collection("chats").document(userUID)
                    .collection(reference).document(readMessages.documentId).update(updateMessageInfo)
                Log.d("efefe", "always ok")
            } catch (e: Exception) {

            }
        }
    } else {
        val result2 = wasReadList.toObjects(SendMessageModel::class.java)

        for (readMessages in result2) {
            updateMessageInfo["wasRead"] = "true"

            try {
                FirebaseFirestore.getInstance().collection("users").document(UID)
                    .collection("chats").document(userUID)
                    .collection(reference).document(readMessages.documentId).update(updateMessageInfo)
            } catch (e: Exception) {

            }
        }
    }
}