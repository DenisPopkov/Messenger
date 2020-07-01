package ru.popkovden.messengerapplication.ui.adapters.chat.messenger.images

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R

class SentImagesRecyclerView(val context: Context) : RecyclerView.Adapter<ReceivedImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivedImagesViewHolder =
        ReceivedImagesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.sent_image_message_item, parent, false)
        )

    override fun getItemCount(): Int = 2
    override fun onBindViewHolder(holder: ReceivedImagesViewHolder, position: Int) {}
}

class SentImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)