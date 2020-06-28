package ru.popkovden.messengerapplication.ui.adapters.chat.messenger.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.ui.fragment.ChatScreenFragmentDirections

class UserMessagesRecyclerView(val context: Context, val fm: FragmentManager) : RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder =
        ChatViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.user_message, parent, false
            )
        )

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) = holder.itemView.run {
        this.setOnClickListener {
            val action = ChatScreenFragmentDirections.actionChatToFragmentMessengerScreen()
            findNavController().navigate(action)
        }
    }
}

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)