package ru.popkovden.messengerapplication.utils.helper

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.model.LastMessageModel
import ru.popkovden.messengerapplication.model.SendMessageModel
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
    val result = wasReadList.toObjects(SendMessageModel::class.java)

    for (readMessages in result) {
        updateMessageInfo["wasRead"] = "true"

        FirebaseFirestore.getInstance().collection("users").document(UID)
            .collection("chats").document(userUID)
            .collection(reference).document(readMessages.documentId).update(updateMessageInfo)
    }
}