package ru.popkovden.messengerapplication.data.repository.auth

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object CheckIfUserExist {

    val firestoreReference = FirebaseFirestore.getInstance().collection("users")

    suspend fun check(phoneNumber: String): Boolean {

        val result = firestoreReference.whereEqualTo("phoneNumber", phoneNumber)
            .get().await()

        if (result.documents != null) {
            return true
        }
         else {
            return false
        }
    }
}