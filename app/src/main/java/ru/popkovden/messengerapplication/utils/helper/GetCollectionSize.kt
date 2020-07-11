package ru.popkovden.messengerapplication.utils.helper

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getCollectionSize(UID: String): Int? {

    val firebaseFirestore = FirebaseFirestore.getInstance().collection("users")
        .document(UID).collection("chats").document("CollectionSize").get().await()

    return firebaseFirestore.get("collectionSize")?.toString()?.toInt()
}

fun updateCollectionSize(UID: String, lastValue: Int) {

    val hashMap = hashMapOf("collectionSize" to lastValue.plus(1))

    FirebaseFirestore.getInstance().collection("users")
        .document(UID).collection("chats").document("CollectionSize").set(hashMap)
}