package ru.popkovden.messengerapplication.ui.adapters.profile.createPost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.ui.fragment.CreatePostFragment.Companion.compressImages

class ImageSliderRecyclerView(val imagesSliderList: ArrayList<String>, val context: Context, val recyclerView: RecyclerView, val direction: NavDirections, val imageButton: AppCompatImageButton?) : RecyclerView.Adapter<ImageSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder =
        ImageSliderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_create_post_list_item, parent, false)
        )

    override fun getItemCount(): Int = imagesSliderList.size

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val currentPosition = imagesSliderList[position]
        Glide.with(context).load(currentPosition).into(holder.imageSlider)

        holder.imageSlider.setOnClickListener {
            Navigation.findNavController(it).navigate(direction)
        }

        holder.deleteImage.setOnClickListener {
            imagesSliderList.removeAt(position)
            compressImages = imagesSliderList
            notifyDataSetChanged()

            if (imagesSliderList.size <= 0) {
                recyclerView.visibility = View.GONE
                imageButton?.setImageResource(R.drawable.microphone_icon)
            }
        }
    }
}

class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageSlider: ShapeableImageView = itemView.findViewById(R.id.sliderImage)
    val deleteImage: AppCompatImageButton = itemView.findViewById(R.id.deleteImage)
}