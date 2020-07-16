package ru.popkovden.messengerapplication.utils.helper

import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.popkovden.messengerapplication.utils.deleteLikeState
import ru.popkovden.messengerapplication.utils.saveLikeState

fun plusLike(previousValue: Int, UID: String, postTitle: String, like: AppCompatTextView) = CoroutineScope(IO).launch {

    val settingsHashMap = mutableMapOf("likeCount" to previousValue.plus(1))

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").update(settingsHashMap as Map<String, Any>)

    withContext(Main) {
        like.text = previousValue.plus(1).toString()
    }
}

fun minusLike(previousValue: Int, UID: String, postTitle: String, like: AppCompatTextView) = CoroutineScope(IO).launch {

    val settingsHashMap = hashMapOf("likeCount" to previousValue.minus(1))

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").update(settingsHashMap as Map<String, Any>)

    withContext(Main) {
        like.text = previousValue.minus(1).toString()
    }
}

fun getLikeCount(UID: String, postTitle: String, counter: Int, like: AppCompatTextView) {

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").get().addOnCompleteListener {
            val likeCount = it.result!!["likeCount"].toString().toInt()

            when (counter) {
                1 -> {
                    plusLike(likeCount, UID, postTitle, like)
                    saveLikeState(UID, postTitle)
                }
                2 -> {
                    minusLike(likeCount, UID, postTitle, like)
                    deleteLikeState(UID, postTitle)
                }
            }
        }
}