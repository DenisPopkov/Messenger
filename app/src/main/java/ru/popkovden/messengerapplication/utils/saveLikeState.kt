package ru.popkovden.messengerapplication.utils

import androidx.appcompat.widget.AppCompatImageButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.R

fun saveLikeState(UID: String, postTitle: String, userUID: String) = CoroutineScope(IO).launch {

    val previousLikesState = getPreviousState(UID, postTitle)
    delay(1000)
    previousLikesState.add(userUID)

    val settingsHashMap = mutableMapOf("saveLikeState" to previousLikesState)

   FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").update(settingsHashMap as Map<String, Any>)
}

fun deleteLikeState(UID: String, postTitle: String, userUID: String) = CoroutineScope(IO).launch {

    val previousLikesState = getPreviousState(UID, postTitle)
    delay(1000)
    previousLikesState.remove(userUID)

    val settingsHashMap = mutableMapOf("saveLikeState" to previousLikesState)

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").update(settingsHashMap as Map<String, Any>)
}

fun getLikeState(UID: String, postTitle: String, imageButton: AppCompatImageButton, userUID: String) {

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").get().addOnCompleteListener {state ->

            val stateList = state.result?.get("saveLikeState") as ArrayList<String>?

            try {
                if (stateList!!.contains(userUID)) {
                    imageButton.setImageResource(R.drawable.like_lined_icon)
                }
            } catch (e: Exception) {

            }
        }
}

private suspend fun getPreviousState(UID: String, postTitle: String): ArrayList<String> {
    val state = FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").get().await()

    val stateList = state?.get("saveLikeState") as ArrayList<String>?

    return stateList!!
}