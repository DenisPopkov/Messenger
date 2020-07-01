package ru.popkovden.messengerapplication.ui.adapters.profile.createPost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import ru.popkovden.messengerapplication.R

class VideoSliderRecyclerView(private val videoList: ArrayList<String>, val context: Context) : RecyclerView.Adapter<VideoSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoSliderViewHolder =
        VideoSliderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.video_slider_create_post_item, parent, false)
        )

    override fun getItemCount(): Int = videoList.size
    override fun onBindViewHolder(holder: VideoSliderViewHolder, position: Int) {

        val currentPosition = videoList[position]
        Glide.with(context).load(currentPosition).into(holder.videoView)
    }
}

class VideoSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val videoView: ShapeableImageView = itemView.findViewById(R.id.videoView)
}