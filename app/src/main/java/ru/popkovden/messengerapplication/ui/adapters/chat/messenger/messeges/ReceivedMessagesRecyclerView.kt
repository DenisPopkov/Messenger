package ru.popkovden.messengerapplication.ui.adapters.chat.messenger.messeges

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R

class ReceivedMessagesRecyclerView(val context: Context) : RecyclerView.Adapter<ReceivedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivedViewHolder =
        ReceivedViewHolder(
            LayoutInflater.from(context).inflate(R.layout.received_messages, parent, false)
        )

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: ReceivedViewHolder, position: Int) {}
}

class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)