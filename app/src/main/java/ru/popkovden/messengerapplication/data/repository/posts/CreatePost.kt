package ru.popkovden.messengerapplication.data.repository.posts

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.PostsModel

object CreatePost {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private var storageReference = firebaseStorage.reference.child("filesFromPosts")
    private var reference: StorageReference? = null
    private val userInfo = hashMapOf<String, Any>()

    private val imageHelper = mutableListOf<String>()

    fun createPost(postInfo: PostsModel, UID: String) = CoroutineScope(IO).launch {

        postInfo.postImages?.forEach { postImage ->
            reference = storageReference.child("$UID-${postInfo.postTitle}").child("image - ${System.currentTimeMillis()}")
            reference?.putFile(Uri.parse(postImage))?.addOnSuccessListener {
                reference?.downloadUrl?.addOnSuccessListener { imageUri ->
                    imageHelper.add(imageUri.toString())
                }
            }

            for (postVideo in postInfo.postVideos!!) {
                storageReference.child(UID + postInfo.postTitle)
                    .child("videos - ${System.currentTimeMillis()}").putFile(Uri.parse(postVideo))
            }

            for (postFiles in postInfo.postFiles!!) {
                storageReference.child(UID + postInfo.postTitle)
                    .child("files - ${System.currentTimeMillis()}").putFile(Uri.parse(postFiles))
            }

            userInfo["postImages"] = imageHelper
            userInfo["postVideos"] = postInfo.postVideos
            userInfo["postFiles"] = postInfo.postFiles
            userInfo["postTitle"] = postInfo.postTitle
            userInfo["postMainText"] = postInfo.postMainText
            userInfo["likeCount"] = postInfo.likeCount

            // Создает пост
            firebaseFirestore.collection("users").document(UID)
                .collection("posts").document().set(userInfo)
        }
    }
}