package ru.popkovden.messengerapplication.ui.adapters.profile.mainPart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import ru.popkovden.messengerapplication.R

class MainProfileRecyclerViewPart(val context: Context, private val UID: String, val image: String, val name: String) : RecyclerView.Adapter<MainProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProfileViewHolder =
        MainProfileViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_recyclerview_part, parent, false))

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: MainProfileViewHolder, position: Int) {

        Log.d("efefe", UID + "uid")
        Glide.with(context).load(image).into(holder.avatar)

        holder.profileName.text = name
    }
}

class MainProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val avatar: ShapeableImageView = itemView.findViewById(R.id.image)
    val profileName: AppCompatTextView = itemView.findViewById(R.id.name)
}