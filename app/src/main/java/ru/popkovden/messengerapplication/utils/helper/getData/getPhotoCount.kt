package ru.popkovden.messengerapplication.utils.helper.getData

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

fun updatePhotoCount(UID: String, imageCount: Int, previousPhotoCount: Int) = CoroutineScope(IO).launch {

    val photoCountHashMap = hashMapOf("imageCount" to previousPhotoCount.plus(imageCount))

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("photoCount").document("photoCount").update(photoCountHashMap as Map<String, Any>)
}

fun setPhotoCount(UID: String) = CoroutineScope(IO).launch {

    val photoCountHashMap = hashMapOf("imageCount" to 0)

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("photoCount").document("photoCount").set(photoCountHashMap)
}

suspend fun getPhotoCount(UID: String): Int {

    val result = FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("photoCount").document("photoCount").get().await()

    return result["imageCount"].toString().toInt()
}