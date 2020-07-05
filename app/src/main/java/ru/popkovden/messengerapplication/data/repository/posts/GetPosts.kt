package ru.popkovden.messengerapplication.data.repository.posts

import android.content.Context
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.PostsModel
import ru.popkovden.messengerapplication.ui.adapters.profile.mainPart.MainProfileRecyclerViewPart
import ru.popkovden.messengerapplication.ui.adapters.profile.mainPart.PostsProfileRecyclerView

object GetPosts{

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val userPosts = mutableListOf<PostsModel>()

    fun getPosts(UID: String, recyclerViewAdapter: RecyclerView, context: Context, image: String, name: String): MutableList<PostsModel> {

        CoroutineScope(IO).launch {
            firebaseFirestore.collection("users")
                .document(UID)
                .collection("posts").addSnapshotListener { querySnapshot, _ ->

                    userPosts.clear()

                    for (document in querySnapshot!!.documents) {
                        val likeCount = document.get("likeCount").toString()
//                        val postFiles = document.get("postFiles") as? ArrayList<String>
//                        val postImages = document.get("postImages") as? ArrayList<String>
                        val postMainText = document.get("postMainText").toString()
                        val postTitle = document.get("postTitle").toString()
//                        val postVideos = document.get("postVideos") as? ArrayList<String>
                        userPosts.add(PostsModel(arrayListOf(), arrayListOf(), arrayListOf(), likeCount, postTitle, postMainText))
                        val mergeAdapter = MergeAdapter(MainProfileRecyclerViewPart(context, image, name), PostsProfileRecyclerView(context, userPosts))
                        recyclerViewAdapter.adapter = mergeAdapter
                        val adapter = recyclerViewAdapter.adapter
                        adapter?.notifyDataSetChanged()
                    }
                }
        }

        return userPosts
    }
}