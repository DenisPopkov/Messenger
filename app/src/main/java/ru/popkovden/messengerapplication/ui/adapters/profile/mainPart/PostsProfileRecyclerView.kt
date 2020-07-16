package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
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
import ru.popkovden.messengerapplication.utils.getLikeState
import ru.popkovden.messengerapplication.utils.helper.getLikeCount

class PostsProfileRecyclerView(val context: Context, private val postsList: List<PostsModel>, val UID: String) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.posts_item, parent, false)
        )

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.itemView.run {

        val postImages = arrayListOf<String>()
        val postVideos = arrayListOf<String>()

        val currentPosition = postsList[position]

        getLikeState(UID, currentPosition.postTitle, likeIcon)

        title.text = currentPosition.postTitle
        mainPostText.text = currentPosition.postMainText
        likeCount.text = currentPosition.likeCount

        for (images in currentPosition.postImages!!) {
            postImages.add(images)
        }

        val mergeAdapter = MergeAdapter(PostFilesSlider(postImages, context))
        mergeAdapter.addAdapter(VideoSliderRecyclerView(postVideos, context))
        postFileSliderRecyclerView.adapter = mergeAdapter

        likeIcon.setOnClickListener {

            val imageDrawable = likeIcon.drawable

            if (imageDrawable.constantState!! == resources.getDrawable(R.drawable.like_outlined_icon).constantState) {
                getLikeCount(UID, currentPosition.postTitle, 1, likeCount)
                likeIcon.setImageDrawable(resources.getDrawable(R.drawable.like_lined_icon))
            } else {
                getLikeCount(UID, currentPosition.postTitle, 2, likeCount)
                likeIcon.setImageDrawable(resources.getDrawable(R.drawable.like_outlined_icon))
            }
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)