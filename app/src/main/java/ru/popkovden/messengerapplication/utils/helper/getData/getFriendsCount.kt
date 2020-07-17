package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getFriendsCount(UID: String): Int {

    val contactList = FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("contacts").get().await()

    return contactList.documents.size
}