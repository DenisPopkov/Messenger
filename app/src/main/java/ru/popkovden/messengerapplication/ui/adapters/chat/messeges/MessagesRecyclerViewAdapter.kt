package ru.popkovden.messengerapplication.ui.adapters.chat.messeges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.received_messages.view.*
import kotlinx.android.synthetic.main.sent_messages.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.MessageModel

const val CONTENT_TYPE_SENT_MESSAGE = 1
const val CONTENT_TYPE_RECEIVED_MESSAGE = 2

class MessagesRecyclerViewAdapter(private var sentMessageList: List<MessageModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SentMessages(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {

            for (message in model.sentMessages!!) {
                itemView.sentMessage.text = message["message"].toString()
                itemView.timeSentMessage.text = message["time"].toString()
            }
        }
    }

    inner class ReceivedMessages(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {

            for (message in model.receivedMessages!!) {
                itemView.receivedMessage.text = message["message"].toString()
                itemView.timeReceivedMessage.text = message["time"].toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CONTENT_TYPE_SENT_MESSAGE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_messages, parent, false)
            SentMessages(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.received_messages, parent, false)
            ReceivedMessages(view)
        }
    }

    override fun getItemCount(): Int = sentMessageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == CONTENT_TYPE_SENT_MESSAGE) {
            (holder as SentMessages).bind(sentMessageList[position])
        } else {
            (holder as ReceivedMessages).bind(sentMessageList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if(sentMessageList[position].CONTENT_TYPE == CONTENT_TYPE_SENT_MESSAGE) {
            CONTENT_TYPE_SENT_MESSAGE
        } else {
            CONTENT_TYPE_RECEIVED_MESSAGE
        }
    }
}