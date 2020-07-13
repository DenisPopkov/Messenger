package ru.popkovden.messengerapplication.utils.helper

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getUserName(phoneNumber: String): String {

    var name = ""

    val firestoreReference = FirebaseFirestore.getInstance()
        .collection("users").whereEqualTo("phoneNumber", phoneNumber).get().await()

    firestoreReference.documents.forEach { documentName ->
        name = documentName["userName"].toString()
    }

    return name
}