package ru.popkovden.messengerapplication.ui.adapters.profile.createPost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import ru.popkovden.messengerapplication.R

class ImageSliderRecyclerView(private val imagesSliderList: ArrayList<String>, val context: Context) : RecyclerView.Adapter<ImageSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder =
        ImageSliderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_create_post_list_item, parent, false)
        )

    override fun getItemCount(): Int = imagesSliderList.size

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val currentPosition = imagesSliderList[position]
        Glide.with(context).load(currentPosition).into(holder.imageSlider)
    }
}

class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageSlider: ShapeableImageView = itemView.findViewById(R.id.sliderImage)
}