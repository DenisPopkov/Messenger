package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_item_for_feed.view.mainPostText
import kotlinx.android.synthetic.main.post_item_for_feed.view.title
import kotlinx.android.synthetic.main.posts_item.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.PostsModel
import ru.popkovden.messengerapplication.ui.adapters.profile.createPost.VideoSliderRecyclerView
import ru.popkovden.messengerapplication.utils.helper.minusLike
import ru.popkovden.messengerapplication.utils.helper.plusLike

class PostsProfileRecyclerView(val context: Context, private val postsList: MutableList<PostsModel>, val UID: String) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.posts_item, parent, false)
        )

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.itemView.run {

        val postImages = arrayListOf<String>()
        val postVideos = arrayListOf<String>()

        val currentPosition = postsList[position]

        title.text = currentPosition.postTitle
        mainPostText.text = currentPosition.postMainText

        for (images in currentPosition.postImages!!) {
            postImages.add(images)
        }

        val mergeAdapter = MergeAdapter(PostFilesSlider(postImages, context))
        mergeAdapter.addAdapter(VideoSliderRecyclerView(postVideos, context))
        postFileSliderRecyclerView.adapter = mergeAdapter

        this.setOnClickListener {
            likeCount.setOnClickListener {
                var counter = 1
                Log.d("efefe", "click")

                if (counter == 1) {
                    likeIcon.setImageDrawable(resources.getDrawable(R.drawable.like_lined_icon))
                    counter = 2
                    plusLike(likeCount.text.toString().toInt(), UID, currentPosition.postTitle)
                } else {
                    likeIcon.setImageDrawable(resources.getDrawable(R.drawable.like_outlined_icon))
                    minusLike(likeCount.text.toString().toInt(), UID, currentPosition.postTitle)
                    counter = 1
                }
            }
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)