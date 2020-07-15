package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Получает количество сообщений в диалоге
suspend fun getCollectionSize(UID: String, userUID: String): Int? {

    val firebaseFirestore = FirebaseFirestore.getInstance().collection("users")
        .document(UID).collection("chats").document(userUID).collection("Size").document("CollectionSize").get().await()

    return firebaseFirestore.get("collectionSize")?.toString()?.toInt()
}

// Обновляет количество сообщений в диалоге
fun updateCollectionSize(UID: String, lastValue: Int, userUID: String) {

    val collectionSizeHashMap = hashMapOf("collectionSize" to lastValue.plus(1))

    FirebaseFirestore.getInstance().collection("users")
        .document(UID).collection("chats").document(userUID).collection("Size")
        .document("CollectionSize").set(collectionSizeHashMap)

    FirebaseFirestore.getInstance().collection("users")
        .document(userUID).collection("chats").document(UID).collection("Size")
        .document("CollectionSize").set(collectionSizeHashMap)
}