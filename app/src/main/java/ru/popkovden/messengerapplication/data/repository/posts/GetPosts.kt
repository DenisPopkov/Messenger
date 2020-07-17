package ru.popkovden.messengerapplication.data.repository.posts

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.PostsModel
import ru.popkovden.messengerapplication.utils.helper.Event

object GetPosts{

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val userPosts = arrayListOf<PostsModel>()

    fun getPosts(UID: String): Event<ArrayList<PostsModel>> {

        CoroutineScope(IO).launch {
            firebaseFirestore.collection("users")
                .document(UID)
                .collection("posts").addSnapshotListener { querySnapshot, _ ->

                    // Получает данные
                    val postsRequestList = querySnapshot?.documents
                    userPosts.clear()

                    // Перебирает данные
                    for (document in postsRequestList!!) {
                        val likeCount = document.get("likeCount").toString()
                        val postImages: ArrayList<String>? = document.get("postImages") as ArrayList<String>?
                        val postMainText = document.get("postMainText").toString()
                        val postTitle = document.get("postTitle").toString()
                        val postVideos = document.get("postVideos") as ArrayList<String>?
                        val id = document["id"] as Long?
                        userPosts.add(PostsModel(postImages, postVideos, likeCount, postTitle, postMainText, id!!))
                    }
                }
        }

        return Event.success(data = userPosts)
    }
}