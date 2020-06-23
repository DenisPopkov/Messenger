package ru.popkovden.messengerapplication.model

import com.google.firebase.Timestamp

data class PostsModel(
    val CONTENT_TYPE: String,
    val miniAvatarImage: String,
    val userNamePostOwner: String,
    val postTimeCreate: Timestamp,
    val postImages: ArrayList<String>,
    val likeCount: String,
    val postTitle: String,
    val UID: String
)