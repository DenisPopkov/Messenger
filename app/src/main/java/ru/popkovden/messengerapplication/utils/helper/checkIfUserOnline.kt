package ru.popkovden.messengerapplication.utils.helper

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

fun setOnlineStatus(status: String) = CoroutineScope(IO).launch {

    val updateHashMap = hashMapOf("onlineStatus" to status)

    try {FirebaseFirestore.getInstance().collection("users")
        .document(InfoAboutUser.UID).update(updateHashMap as Map<String, Any>)

    } catch (e: Exception) {

    }


}

suspend fun getOnlineStatus(userUID: String): String {

    val status = FirebaseFirestore.getInstance().collection("users")
        .document(userUID).get().await()

    return status["onlineStatus"].toString()
}