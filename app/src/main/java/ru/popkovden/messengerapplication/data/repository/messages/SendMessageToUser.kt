package ru.popkovden.messengerapplication.data.repository.messages

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.utils.helper.getData.getCollectionSize
import ru.popkovden.messengerapplication.utils.helper.getData.getCurrentDateTime
import ru.popkovden.messengerapplication.utils.helper.getData.setLastMessage
import ru.popkovden.messengerapplication.utils.helper.getData.toString

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
        sentMessage["wasRead"] = "false"
        sentMessage["uidSender"] = sendMessageModel.uidSender
        sentMessage["id"] = getCollectionSize(UID, UserUID)!!
        sentMessage["CONTENT_TYPE"] = 1

        // Наполняет данные для "полученного" другим пользователем сообщением
        receivedMessage["message"] = sendMessageModel.message
        receivedMessage["time"] = sendMessageModel.time
        receivedMessage["wasRead"] = "false"
        receivedMessage["uidSender"] = sendMessageModel.uidSender
        receivedMessage["id"] = getCollectionSize(UID, UserUID)!!
        receivedMessage["CONTENT_TYPE"] = 2

        // Отправляет в БД себе на телефон, с типом сообщения - "отправленное"
        firebaseFirestore.collection("users").document(UID)
            .collection("chats").document(UserUID).collection("sentMessages").add(sentMessage)
            .addOnSuccessListener {
                val documentId = it.id
                sentMessage["documentId"] = documentId
                it.update(sentMessage)
            }

        setLastMessage(UID, UserUID, sendMessageModel.message, sendMessageModel.time, getCurrentDateTime().toString("dd-M-yyyy"), "false")

        // Отправляет в БД себе на телефон, с типом сообщения - "полученное"
        firebaseFirestore.collection("users").document(UserUID)
            .collection("chats").document(UID).collection("receivedMessages").add(receivedMessage)
            .addOnSuccessListener {
                val documentId = it.id
                receivedMessage["documentId"] = documentId
                it.update(receivedMessage)
            }

        setLastMessage(UserUID, UID, sendMessageModel.message, sendMessageModel.time, getCurrentDateTime().toString("dd-M-yyyy"), "false")
    }
}