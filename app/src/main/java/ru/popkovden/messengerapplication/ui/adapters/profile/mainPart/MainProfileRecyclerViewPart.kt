package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.utils.helper.getData.getCountOfPosts

class MainProfileRecyclerViewPart(val context: Context, val image: String, val name: String, val UID: String) : RecyclerView.Adapter<MainProfileViewHolder>() {

    private var postsCount: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProfileViewHolder =
        MainProfileViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_recyclerview_part, parent, false))

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: MainProfileViewHolder, position: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            postsCount = getCountOfPosts(UID) // Получение количества постов
        }

        if (!postsCount.toString().isBlank()) {
            holder.countPost.text = postsCount.toString()
        }

        Glide.with(context).load(image).into(holder.avatar)
        holder.profileName.text = name
    }
}

class MainProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val avatar: ShapeableImageView = itemView.findViewById(R.id.image)
    val profileName: AppCompatTextView = itemView.findViewById(R.id.name)
    val countPost: AppCompatTextView = itemView.findViewById(R.id.post_count)
}