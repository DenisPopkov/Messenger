package ru.popkovden.messengerapplication.ui.adapters.chat.messeges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.received_messages.view.*
import ru.popkovden.messengerapplication.R

class ReceivedMessagesRecyclerView(private val receivedMessageList: List<HashMap<String, Any>>) : RecyclerView.Adapter<ReceivedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivedViewHolder =
        ReceivedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.received_messages, parent, false)
        )

    override fun getItemCount(): Int = receivedMessageList.size
    override fun onBindViewHolder(holder: ReceivedViewHolder, position: Int) = holder.itemView.run {

        val currentItem = receivedMessageList[position]

        receivedMessage.text = currentItem["message"].toString()
        timeReceivedMessage.text = currentItem["time"].toString()
    }
}

class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)