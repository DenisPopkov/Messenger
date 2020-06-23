package ru.popkovden.messengerapplication.ui.adapters.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.posts_item.view.*
import ru.popkovden.messengerapplication.R

class PostsProfileRecyclerView(val context: Context, private val value: List<String>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.posts_item, parent, false)
        )

    override fun getItemCount(): Int = value.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.itemView.run {
        val currentPosition = value[position]
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)