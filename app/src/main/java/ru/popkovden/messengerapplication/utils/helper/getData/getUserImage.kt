package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getUserImage(phoneNumber: String): String {

    var image = ""

    val firestoreReference = FirebaseFirestore.getInstance()
        .collection("users").whereEqualTo("phoneNumber", phoneNumber).get().await()

    firestoreReference.documents.forEach { document ->
        image = document["userProfileImage"].toString()
    }

    return image
}