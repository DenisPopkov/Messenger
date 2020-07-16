package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun getCountOfPosts(UID: String): Int {

    val result = FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").get().await()

    return result.documents.size
}