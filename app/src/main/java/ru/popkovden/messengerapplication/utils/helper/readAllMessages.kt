package ru.popkovden.messengerapplication.utils.helper

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

suspend fun readAllMessagesInChat(userUID: String) {

    val wasReadList = FirebaseFirestore.getInstance().collection("users").document(userUID)
        .collection("chats").document(InfoAboutUser.UID)
        .collection("sentMessages").get().await()

    for (readMessages in wasReadList.documents) {
        Log.d("efefe", readMessages.toString() + " wasReadList")
    }
}

fun readLastMessageOrNot() {


}