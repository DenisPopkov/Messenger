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
    private val userPosts = arrayListOf<PostsModel>()

    fun getPosts(recyclerViewAdapter: RecyclerView, UID: String, context: Context, image: String, name: String): MutableList<PostsModel>? {

        CoroutineScope(IO).launch {
            firebaseFirestore.collection("users")
                .document(UID)
                .collection("posts").get().addOnSuccessListener {

                    // Получает данные
                    val postsRequestList = it.documents
                    userPosts.clear()

                    // Настраивает параметры адаптера
                    val mergeAdapter = MergeAdapter()
                    recyclerViewAdapter.adapter = mergeAdapter

                    // Основная часть адаптера
                    mergeAdapter.addAdapter(MainProfileRecyclerViewPart(context, image, name, UID))

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

                    // Устанавливает адаптер с постами
                    mergeAdapter.addAdapter(PostsProfileRecyclerView(context,
                        userPosts.sortedWith(compareBy { it.id }).toMutableList(), UID))
                }
        }

        return userPosts
    }
}