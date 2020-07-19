package ru.popkovden.messengerapplication.utils.helper.getData

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.utils.helper.calculateTime

// Отправляет в БД последнее сообщение из диалога
fun setLastMessage(UID: String, userUID: String, lastMessage: String, timeSend: String, daySend: String) {

    val lastMessageHashMap = hashMapOf<String, String>()
    lastMessageHashMap["lastMessage"] = lastMessage
    lastMessageHashMap["timeSend"] = timeSend
    lastMessageHashMap["uidSender"] = userUID
    lastMessageHashMap["daySend"] = daySend

    FirebaseFirestore.getInstance().collection("users").document(UID).collection("contacts")
        .document(userUID).collection("lastMessage").document("last").set(lastMessageHashMap as Map<String, Any>)

    FirebaseFirestore.getInstance().collection("users").document(userUID).collection("contacts")
        .document(UID).collection("lastMessage").document("last").set(lastMessageHashMap as Map<String, Any>)
}

// Получает последнее сообщение из диалога
suspend fun getLastMessage(UID: String, userUID: String, minute: String, hour: String, day: String): HashMap<String, String> {
    val lastMessage = FirebaseFirestore.getInstance().collection("users").document(UID).collection("contacts")
        .document(userUID).collection("lastMessage").document("last").get().await()

    var predicateMessage = lastMessage["lastMessage"].toString()
    val timeSend = lastMessage["timeSend"].toString()
    val daySend = lastMessage["daySend"].toString()
    var user = ""

    if (lastMessage["uidSender"].toString() == UID) {
        user = "Вы: "
        Log.d("efefe", "${lastMessage["uidSender"].toString()} uidSender")
        Log.d("efefe", "$UID uid")
    }

    if (predicateMessage.length > 20) {
        predicateMessage = lastMessage["lastMessage"].toString().take(10) + "... " + calculateTime(timeSend, minute, hour, day, daySend)
    }

    val lastMessageHashMap = hashMapOf<String, String>()
    lastMessageHashMap["uidSender"] = user
    lastMessageHashMap["lastMessage"] = predicateMessage
    lastMessageHashMap["daySend"] = daySend
    lastMessageHashMap["timeSend"] = calculateTime(timeSend, minute, hour, day, daySend)

    return lastMessageHashMap
}