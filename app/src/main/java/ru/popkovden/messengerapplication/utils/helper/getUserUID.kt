package ru.popkovden.messengerapplication.utils.helper

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getUserUID(phoneNumber: String): String {

    var UID = ""

    val firestoreReference = FirebaseFirestore.getInstance()
        .collection("users").whereEqualTo("phoneNumber", phoneNumber).get().await()

    firestoreReference.documents.forEach { document ->
        UID = document["UID"].toString()
    }

    return UID
}