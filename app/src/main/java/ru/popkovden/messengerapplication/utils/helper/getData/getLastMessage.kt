package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.utils.helper.calculateTime

// Отправляет в БД последнее сообщение из диалога
fun setLastMessage(UID: String, userUID: String, lastMessage: String, timeSend: String){

    val lastMessageHashMap = hashMapOf<String, String>()
    lastMessageHashMap["lastMessage"] = lastMessage
    lastMessageHashMap["timeSend"] = timeSend
    lastMessageHashMap["uidSender"] = UID

    FirebaseFirestore.getInstance().collection("users").document(UID).collection("chats")
        .document(userUID).collection("lastMessage").document("last").set(lastMessageHashMap)
}

// Получает последнее сообщение из диалога
suspend fun getLastMessage(UID: String, userUID: String, minute: String, hour: String): MutableMap<String, String> {
    val lastMessage = FirebaseFirestore.getInstance().collection("users").document(UID).collection("chats")
        .document(userUID).collection("lastMessage").document("last").get().await()

    var predicateMessage = lastMessage["lastMessage"].toString()
    var user = ""
    val timeSend = lastMessage["timeSend"].toString()

    if (lastMessage["uidSender"].toString() == UID) {
        user = "Вы: "
    }

    if (predicateMessage.length > 20) {
        predicateMessage = lastMessage["lastMessage"].toString().take(20) + "... " + calculateTime(timeSend, minute, hour)
    }

    val lastMessageHashMap = hashMapOf<String, String>()
    lastMessageHashMap["whoSend"] = user
    lastMessageHashMap["lastMessage"] = predicateMessage
    lastMessageHashMap["time"] = calculateTime(timeSend, minute, hour)

    return lastMessageHashMap
}