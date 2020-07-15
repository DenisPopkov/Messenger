package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Отправляет в БД последнее сообщение из диалога
fun setLastMessage(UID: String, userUID: String, lastMessage: String){

    val lastMessageHashMap = hashMapOf<String, String>()
    lastMessageHashMap["lastMessage"] = lastMessage

    FirebaseFirestore.getInstance().collection("users").document(UID).collection("chats")
        .document(userUID).collection("lastMessage").document("last").set(lastMessageHashMap)
}

// Получает последнее сообщение из диалога
suspend fun getLastMessage(UID: String, userUID: String): String {
    val lastMessage = FirebaseFirestore.getInstance().collection("users").document(UID).collection("chats")
        .document(userUID).collection("lastMessage").document("last").get().await()

    var predicateMessage = lastMessage["lastMessage"].toString()

    if (predicateMessage.length > 20) {
        predicateMessage = lastMessage["lastMessage"].toString().take(20) + "..."
    }

    return predicateMessage
}