package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getCollectionSize(UID: String, userUID: String): Int? {

    val firebaseFirestore = FirebaseFirestore.getInstance().collection("users")
        .document(UID).collection("chats").document(userUID).collection("Size").document("CollectionSize").get().await()

    return firebaseFirestore.get("collectionSize")?.toString()?.toInt()
}

fun updateCollectionSize(UID: String, lastValue: Int, userUID: String) {

    val hashMap = hashMapOf("collectionSize" to lastValue.plus(1))

    FirebaseFirestore.getInstance().collection("users")
        .document(UID).collection("chats").document(userUID).collection("Size").document("CollectionSize").set(hashMap)

    FirebaseFirestore.getInstance().collection("users")
        .document(userUID).collection("chats").document(UID).collection("Size").document("CollectionSize").set(hashMap)
}