package ru.popkovden.messengerapplication.utils.helper

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

fun updateScreenDestination(screen: String, whichDialog: String) = CoroutineScope(IO).launch {

    val updateHashMap = hashMapOf("screen" to "$screen-$whichDialog")

    try {
        FirebaseFirestore.getInstance().collection("users")
            .document(InfoAboutUser.UID).update(updateHashMap as Map<String, Any>)
    } catch (e: Exception) {

    }
}

suspend fun getScreenDestination(userUID: String): String {

    val screenResult = FirebaseFirestore.getInstance().collection("users")
        .document(userUID).get().await()

    return screenResult["screen"].toString()
}