package ru.popkovden.messengerapplication.ui.adapters.chat.messeges

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.received_image_message_item.view.*
import kotlinx.android.synthetic.main.received_messages.view.*
import kotlinx.android.synthetic.main.sent_image_message_item.view.*
import kotlinx.android.synthetic.main.sent_messages.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.MessageModel

const val CONTENT_TYPE_SENT_MESSAGE = 1
const val CONTENT_TYPE_RECEIVED_MESSAGE = 2
const val CONTENT_TYPE_SENT_IMAGES = 3
const val CONTENT_TYPE_RECEIVED_IMAGES = 4

class MessagesRecyclerViewAdapter(var sentMessageList: List<MessageModel>, val context: Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SentMessages(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {

            for (message in model.sentMessages!!) {
                itemView.sentMessage.text = message["message"].toString()
                itemView.timeSentMessage.text = message["time"].toString()

                if (message["wasRead"] == "true") {
                    itemView.readStatusSentMessage.visibility = View.GONE
                }
            }
        }
    }

    inner class ReceivedMessages(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {

            for (message in model.receivedMessages!!) {
                itemView.receivedMessage.text = message["message"].toString()
                itemView.timeReceivedMessage.text = message["time"].toString()

                if (message["wasRead"] == "true") {
                    itemView.readStatusReceivedMessage.visibility = View.GONE
                }
            }
        }
    }

    inner class SentImagesMessage(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {

            try {
                for (image in model.sentImages!!) {
                    Log.d("efefe", image["image"].toString())
                    Glide.with(context).load(image["image"]).into(itemView.sentImage)
                }
            } catch (e: Exception) {

            }
        }
    }

    inner class ReceivedImagesMessage(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(model: MessageModel) {

            try {
                for (image in model.receivedImages!!) {
                    Glide.with(context).load(image["image"].toString()).into(itemView.receivedImage)
                }
            }catch (e: Exception) {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CONTENT_TYPE_SENT_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_messages, parent, false)
                SentMessages(view)
            }
            CONTENT_TYPE_RECEIVED_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.received_messages, parent, false)
                ReceivedMessages(view)
            }
            CONTENT_TYPE_RECEIVED_IMAGES -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.received_image_message_item, parent, false)
                ReceivedImagesMessage(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_image_message_item, parent, false)
                SentImagesMessage(view)
            }
        }
    }

    override fun getItemCount(): Int = sentMessageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when {
            getItemViewType(position) == CONTENT_TYPE_SENT_MESSAGE -> {
                (holder as SentMessages).bind(sentMessageList[position])
            }
            getItemViewType(position) == CONTENT_TYPE_RECEIVED_MESSAGE -> {
                (holder as ReceivedMessages).bind(sentMessageList[position])
            }
            getItemViewType(position) == CONTENT_TYPE_RECEIVED_IMAGES -> {
                (holder as ReceivedImagesMessage).bind(sentMessageList[position])
            }
            else -> {
                (holder as SentImagesMessage).bind(sentMessageList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (sentMessageList[position].CONTENT_TYPE) {
            CONTENT_TYPE_SENT_MESSAGE -> {
                CONTENT_TYPE_SENT_MESSAGE
            }
            CONTENT_TYPE_RECEIVED_MESSAGE -> {
                CONTENT_TYPE_RECEIVED_MESSAGE
            }
            CONTENT_TYPE_RECEIVED_IMAGES -> {
                CONTENT_TYPE_RECEIVED_IMAGES
            }
            else -> {
                CONTENT_TYPE_SENT_IMAGES
            }
        }
    }
}