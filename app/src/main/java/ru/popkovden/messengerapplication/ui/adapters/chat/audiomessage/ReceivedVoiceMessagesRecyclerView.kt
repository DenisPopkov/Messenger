package ru.popkovden.messengerapplication.ui.adapters.chat.audiomessage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R

class ReceivedVoiceMessagesRecyclerView(val context: Context) : RecyclerView.Adapter<SentAudioMessagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentAudioMessagesViewHolder =
        SentAudioMessagesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.received_voice_messages, parent, false)
        )

    override fun getItemCount(): Int = 2
    override fun onBindViewHolder(holder: SentAudioMessagesViewHolder, position: Int) {}
}

class ReceivedAudioMessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)