package ru.popkovden.messengerapplication.ui.adapters.chat.contacts

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_message.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.model.ContactFriendModel
import ru.popkovden.messengerapplication.ui.fragment.ChatScreenFragmentDirections

class UserMessagesRecyclerView(val context: Context, val contacts: ArrayList<ContactFriendModel>, val UID: String, val minute: String, val hour: String, val day: String) : RecyclerView.Adapter<ChatViewHolder>() {

    private var lastMessageHashMap = arrayListOf<HashMap<String, String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder = ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.user_message, parent, false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) = holder.itemView.run {

        val currentContact = contacts[position]

        Log.d("efefe", currentContact.lastMessage["uidSender"].toString() + " joefhei")

        if (currentContact.lastMessage.isNullOrEmpty()) {
            whoSend.setTextColor(resources.getColor(R.color.lightText))
            whoSend.text = resources.getString(R.string.no_messages_yet)
        }

        if (currentContact.lastMessage["uidSender"].toString().isBlank()) {
            whoSend.setTextColor(resources.getColor(R.color.lightText))
            lastMessage.setTextColor(resources.getColor(R.color.darkerText))
            whoSend.text = currentContact.lastMessage["lastMessage"].toString()
            lastMessage.text = currentContact.lastMessage["timeSend"].toString()
        } else {
            whoSend.text = resources.getString(R.string.who_send)
            lastMessage.text = " " + currentContact.lastMessage["lastMessage"].toString() // Получает последнее сообщение
            timeSend.text = currentContact.lastMessage["timeSend"].toString()
        }

        contactName.text = currentContact.contactName
        Glide.with(context).load(currentContact.contactPhoto).into(userImage)

        this.setOnClickListener {
            val action = ChatScreenFragmentDirections.actionChatToFragmentMessengerScreen(currentContact.contactUID, currentContact.contactName, currentContact.contactPhoto)
            findNavController().navigate(action)
        }
    }
}

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)