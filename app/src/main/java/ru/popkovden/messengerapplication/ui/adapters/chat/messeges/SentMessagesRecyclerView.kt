package ru.popkovden.messengerapplication.ui.adapters.chat.messenger.messeges

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R

class SentMessagesRecyclerView(val context: Context) : RecyclerView.Adapter<SentMessagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentMessagesViewHolder =
        SentMessagesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.sent_messages, parent, false)
        )

    override fun getItemCount(): Int = 2
    override fun onBindViewHolder(holder: SentMessagesViewHolder, position: Int) {}
}

class SentMessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)