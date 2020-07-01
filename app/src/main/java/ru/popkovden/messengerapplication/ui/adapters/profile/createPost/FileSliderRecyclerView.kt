package ru.popkovden.messengerapplication.ui.adapters.profile.createPost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ru.popkovden.messengerapplication.R

class FileSliderRecyclerView(private val documentList: ArrayList<String>, val context: Context) : RecyclerView.Adapter<FileSliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileSliderViewHolder =
        FileSliderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.file_slider_create_post_item, parent, false)
        )

    override fun getItemCount(): Int = documentList.size
    override fun onBindViewHolder(holder: FileSliderViewHolder, position: Int) {
        val currentPosition = documentList[position]
//        Glide.with(context).load(R.drawable.image_placeholder).into(holder.fileView)
//        holder.fileView.setImageDrawable()
    }
}

class FileSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fileView: ShapeableImageView = itemView.findViewById(R.id.fileView)
}