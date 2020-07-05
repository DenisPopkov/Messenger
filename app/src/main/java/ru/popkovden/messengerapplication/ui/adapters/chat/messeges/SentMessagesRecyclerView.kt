package ru.popkovden.messengerapplication.ui.adapters.chat.messeges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sent_messages.view.*
import ru.popkovden.messengerapplication.R

class SentMessagesRecyclerView(private val sentMessageList: ArrayList<HashMap<String, Any>>) : RecyclerView.Adapter<SentMessagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentMessagesViewHolder =
        SentMessagesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sent_messages, parent, false)
        )

    override fun getItemCount(): Int = sentMessageList.size

    override fun onBindViewHolder(holder: SentMessagesViewHolder, position: Int) = holder.itemView.run {

        val currentItem = sentMessageList[position]

        sentMessage.text = currentItem["message"].toString()
        timeSentMessage.text = currentItem["time"].toString()
    }
}

class SentMessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)