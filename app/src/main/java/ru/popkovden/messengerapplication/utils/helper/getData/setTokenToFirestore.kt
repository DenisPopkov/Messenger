package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

fun setToken(UID: String, token: String) = CoroutineScope(IO).launch {

    val tokenInfo = hashMapOf("token" to token)

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .update(tokenInfo as Map<String, Any>)
}

suspend fun getToken(userUID: String): String {

    val result = FirebaseFirestore.getInstance().collection("users").document(userUID)
        .get().await()

    return result["token"].toString()
}