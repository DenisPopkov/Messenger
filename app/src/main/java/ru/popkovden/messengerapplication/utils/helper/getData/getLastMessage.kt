package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

fun setLastMessage(UID: String, userUID: String, lastMessage: String){

    val hashMap = hashMapOf<String, String>()
    hashMap["lastMessage"] = lastMessage

    FirebaseFirestore.getInstance().collection("users").document(UID).collection("chats")
        .document(userUID).collection("lastMessage").document("last").set(hashMap)
}

suspend fun getLastMessage(UID: String, userUID: String): String {
    val lastMessage = FirebaseFirestore.getInstance().collection("users").document(UID).collection("chats")
        .document(userUID).collection("lastMessage").document("last").get().await()

    return lastMessage["lastMessage"].toString()
}