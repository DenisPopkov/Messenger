package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.PostsModel

class PostsProfileRecyclerView(val context: Context, private val postsList: ArrayList<PostsModel>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.posts_item, parent, false)
        )

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        for (post in postsList) {
            Log.d("efefe", post.postTitle)
            Log.d("efefe", post.postMainText)
            holder.title.text = post.postTitle
            holder.mainPostText.text = post.postMainText

            for (images in post.postImages!!) {
                Glide.with(context).load(images).into(holder.imagePost)
            }
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: AppCompatTextView = itemView.findViewById(R.id.title)
    val mainPostText: AppCompatTextView = itemView.findViewById(R.id.mainPostText)
    val imagePost: AppCompatImageView = itemView.findViewById(R.id.postImage)
}