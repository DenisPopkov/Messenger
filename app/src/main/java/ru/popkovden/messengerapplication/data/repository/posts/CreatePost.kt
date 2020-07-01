package ru.popkovden.messengerapplication.data.repository.posts

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import ru.popkovden.messengerapplication.model.PostsModel

object CreatePost {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val storageReference = firebaseStorage.reference.child("filesFromPosts")
    private val userInfo = hashMapOf<String, Any>()

    fun createPost(postInfo: PostsModel, UID: String) {

        // Добавляет фото, видео, файлы в БД
        for (postImage in postInfo.postImages!!) {
            storageReference.child(UID + postInfo.postTitle)
                .child("images").putFile(Uri.parse(postImage))
        }

        for (postVideo in postInfo.postVideos!!) {
            storageReference.child(UID + postInfo.postTitle)
                .child("videos").putFile(Uri.parse(postVideo))
        }

        for (postFiles in postInfo.postDocuments!!) {
            storageReference.child(UID + postInfo.postTitle)
                .child("files").putFile(Uri.parse(postFiles))
        }

        userInfo["postImages"] = postInfo.postImages
        userInfo["postVideos"] = postInfo.postVideos
        userInfo["postFiles"] = postInfo.postDocuments
        userInfo["postTitle"] = postInfo.postTitle
        userInfo["postMainText"] = postInfo.postMainText
        userInfo["likeCount"] = postInfo.likeCount

        // Создает пост
        firebaseFirestore.collection("users").document(UID)
            .collection("posts").document().set(userInfo)
    }
}