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
    private val storageReference = firebaseStorage.reference.child("filesFromPosts")
    private var reference: StorageReference? = null
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val userInfo = hashMapOf<String, Any>()
    private val imageHelper = arrayListOf<String>()

    fun createPost(postInfo: PostsModel, UID: String) = CoroutineScope(IO).launch {

        userInfo.clear()
        imageHelper.clear()

        postInfo.postImages?.forEach { postImage ->
            reference = storageReference.child("$UID-${postInfo.postTitle}").child("image - ${System.currentTimeMillis()}")
            reference?.putFile(Uri.parse(postImage))?.addOnSuccessListener {

                val result = it.metadata!!.reference!!.downloadUrl

                result.addOnSuccessListener { uri ->

                    imageHelper.add(uri.toString())
                }.addOnCompleteListener {
                    if (postInfo.postImages.size == imageHelper.size) {
                        sendPost(postInfo, UID)
                    }
                }
            }
        }
    }

    private fun sendPost(postInfo: PostsModel, UID: String) = CoroutineScope(IO).launch {
        userInfo["postImages"] = imageHelper
        userInfo["postVideos"] = imageHelper
        userInfo["postTitle"] = postInfo.postTitle
        userInfo["postMainText"] = postInfo.postMainText
        userInfo["likeCount"] = postInfo.likeCount

        // Создает пост
        firebaseFirestore.collection("users").document(UID)
            .collection("posts").document().set(userInfo)
    }
}