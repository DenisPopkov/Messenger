package ru.popkovden.messengerapplication.ui.adapters.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R

class ChatRecyclerViewPart(val context: Context) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.search_button, parent, false))

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {}
}

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)