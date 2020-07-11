package ru.popkovden.messengerapplication.ui.adapters.chat.contacts

import android.content.Context
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

class UserMessagesRecyclerView(val context: Context, val contacts: ArrayList<ContactFriendModel>) : RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder = ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.user_message, parent, false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) = holder.itemView.run {

        val currentContact = contacts[position]

        contactName12.text = currentContact.contactName
        lastMessage.text = "Как дела?"
        Glide.with(context).load(currentContact.contactPhoto).into(userImage)

        this.setOnClickListener {
            val action = ChatScreenFragmentDirections.actionChatToFragmentMessengerScreen(currentContact.contactUID, currentContact.contactName, currentContact.contactPhoto)
            findNavController().navigate(action)
        }
    }
}

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)