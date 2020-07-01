package ru.popkovden.messengerapplication.data.repository.posts

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.PostsModel

object GetPosts {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val userPosts = arrayListOf<PostsModel>()

    fun getAllPosts(UID: String): ArrayList<PostsModel> {

        CoroutineScope(IO).launch {
            firebaseFirestore.collection("users")
                .document(UID)
                .collection("posts").get()
                .addOnSuccessListener { documentSnapshot ->
                    val postItem = documentSnapshot.toObjects(PostsModel::class.java)
                    userPosts.clear()
                    userPosts.addAll(postItem)
                }
        }

        return userPosts
    }
}