package ru.popkovden.messengerapplication.ui.adapters.zoom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import ru.popkovden.messengerapplication.R

class ZoomImageViewPager(private val zoomImages: MutableList<String>, val context: Context) : RecyclerView.Adapter<ZoomImageSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZoomImageSliderViewHolder =
        ZoomImageSliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.zoom_image_item, parent, false))

    override fun getItemCount(): Int = zoomImages.size

    override fun onBindViewHolder(holder: ZoomImageSliderViewHolder, position: Int) {

        val currentPosition = zoomImages[position]

        Glide.with(context).load(currentPosition).into(holder.zoomImage)
    }
}

class ZoomImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val zoomImage: PhotoView = itemView.findViewById(R.id.zoomImage)
}