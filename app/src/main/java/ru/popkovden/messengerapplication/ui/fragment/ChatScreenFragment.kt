package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_toolbar_for_chat_screen.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentChatScreenBinding
import ru.popkovden.messengerapplication.ui.adapters.chat.contacts.UserMessagesRecyclerView

class ChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentChatScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_screen, container, false)

        binding.chatScreenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatScreenRecyclerView.adapter =
            UserMessagesRecyclerView(
                requireContext()
            )

        binding.chatToolbar.writeToNewContact.setOnClickListener {
            findNavController().navigate(ChatScreenFragmentDirections.actionChatToContactsFragment())
        }

        return binding.root
    }
}