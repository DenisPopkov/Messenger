package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentChatScreenBinding
import ru.popkovden.messengerapplication.ui.adapters.chat.UserMessagesRecyclerView

class ChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentChatScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_screen, container, false)

        binding.chatScreenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatScreenRecyclerView.adapter = UserMessagesRecyclerView(requireContext())

        return binding.root
    }
}