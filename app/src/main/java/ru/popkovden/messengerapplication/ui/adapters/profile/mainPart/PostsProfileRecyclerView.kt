package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.PostsModel
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.VideoSliderRecyclerView

class PostsProfileRecyclerView(val context: Context, private val postsList: MutableList<PostsModel>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.posts_item, parent, false)
        )

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val postImages = arrayListOf<String>()
        val postVideos = arrayListOf<String>()

        for (post in postsList) {
            holder.title.text = post.postTitle
            holder.mainPostText.text = post.postMainText
            postImages.addAll(post.postImages!!)
            postVideos.addAll(post.postVideos!!)
        }

        val mergeAdapter = MergeAdapter(PostFilesSlider(postImages, context))
        mergeAdapter.addAdapter(VideoSliderRecyclerView(postVideos, context))
        holder.recyclerView.adapter = mergeAdapter
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: AppCompatTextView = itemView.findViewById(R.id.title)
    val mainPostText: AppCompatTextView = itemView.findViewById(R.id.mainPostText)
    val recyclerView: ViewPager2 = itemView.findViewById(R.id.postFileSliderRecyclerView)
}