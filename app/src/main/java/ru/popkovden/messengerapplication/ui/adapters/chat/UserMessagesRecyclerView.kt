package ru.popkovden.messengerapplication.ui.adapters.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R

class UserMessagesRecyclerView(val context: Context) : RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder = ChatViewHolder(LayoutInflater.from(context).inflate(
        R.layout.user_message, parent, false))

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {}
}

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)