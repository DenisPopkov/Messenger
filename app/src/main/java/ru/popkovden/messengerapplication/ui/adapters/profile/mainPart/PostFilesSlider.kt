package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.image_create_post_list_item.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.ui.fragment.UserProfileFragmentDirections

class PostFilesSlider(private val imagesSliderList: ArrayList<String>, val context: Context) : RecyclerView.Adapter<FileSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileSliderViewHolder =
        FileSliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.files_slider_for_user_profile, parent, false), imagesSliderList)

    override fun getItemCount(): Int = imagesSliderList.size

    override fun onBindViewHolder(holder: FileSliderViewHolder, position: Int) {

        val currentPosition = imagesSliderList[position]
        Glide.with(context).load(currentPosition).placeholder(R.drawable.placeholder).into(holder.fileContainer)
    }
}

class FileSliderViewHolder(itemView: View, imagesSliderList: ArrayList<String>) : RecyclerView.ViewHolder(itemView) {
    val fileContainer: ShapeableImageView = itemView.findViewById(R.id.sliderImage)

    init { itemView.setOnClickListener{}

        itemView.sliderImage.setOnClickListener {
            Navigation.findNavController(it).navigate(UserProfileFragmentDirections.actionAccountToZoomImagesFragment(imagesSliderList.toTypedArray()))
        }
    }
}