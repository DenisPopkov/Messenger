package ru.popkovden.messengerapplication.utils.helper

import com.google.firebase.firestore.FirebaseFirestore

fun plusLike(previousValue: Int, UID: String, postTitle: String) {

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").set("likeCount" to previousValue.plus(1))
}

fun minusLike(previousValue: Int, UID: String, postTitle: String) {

    FirebaseFirestore.getInstance().collection("users").document(UID)
        .collection("posts").document("$UID-$postTitle").set("likeCount" to previousValue.minus(1))
}