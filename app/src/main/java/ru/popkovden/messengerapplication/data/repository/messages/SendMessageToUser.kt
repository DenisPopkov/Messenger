package ru.popkovden.messengerapplication.data.repository.messages

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.utils.helper.getCollectionSize

object SendMessageToUser {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var sentMessage: HashMap<String, Any> = hashMapOf()
    private var receivedMessage: HashMap<String, Any> = hashMapOf()

    fun sendMessage(UID: String, UserUID: String, sendMessageModel: SendMessageModel) = CoroutineScope(IO).launch {

        sentMessage.clear()
        receivedMessage.clear()

        // Наполняет данные для "отправленного" сообщения"
        sentMessage["message"] = sendMessageModel.message
        sentMessage["time"] = sendMessageModel.time
        sentMessage["uidSender"] = sendMessageModel.uidSender
        sentMessage["id"] = getCollectionSize(UID)!!
        sentMessage["CONTENT_TYPE"] = 1

        // Наполняет данные для "полученного" другим пользователем сообщением
        receivedMessage["message"] = sendMessageModel.message
        receivedMessage["time"] = sendMessageModel.time
        receivedMessage["uidSender"] = sendMessageModel.uidSender
        receivedMessage["id"] = getCollectionSize(UID)!!
        receivedMessage["CONTENT_TYPE"] = 2

        Log.d("efefe", "$UserUID - userUID")
        Log.d("efefe", "$UID - uid")

        // Отправляет в БД
        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("sentMessages").add(sentMessage)

        firebaseFirestore.collection("users").document(UserUID)
            .collection("chats").document(UID).collection("receivedMessages").add(receivedMessage)
    }
}