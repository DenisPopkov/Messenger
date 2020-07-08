package ru.popkovden.messengerapplication.data.repository.messages

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.SendMessageModel

object SendMessageToUser {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var sentMessage: HashMap<String, Any> = hashMapOf()

    fun sendMessage(UID: String, UserUID: String, sendMessageModel: SendMessageModel) = CoroutineScope(IO).launch {

        sentMessage.clear()

        sentMessage["message"] = sendMessageModel.message
        sentMessage["time"] = sendMessageModel.time
        sentMessage["uidSender"] = sendMessageModel.uidSender
        sentMessage["id"] = sendMessageModel.id

        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("sentMessages").add(sentMessage)
    }
}