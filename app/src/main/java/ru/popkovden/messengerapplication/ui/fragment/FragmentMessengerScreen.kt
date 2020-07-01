package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import kotlinx.android.synthetic.main.toolbar_for_messaging.view.*
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentMessengerScreenBinding
import ru.popkovden.messengerapplication.ui.adapters.chat.messenger.audiomessage.ReceivedVoiceMessagesRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.chat.messenger.audiomessage.SentVoiceMessagesRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.chat.messenger.images.ReceivedImagedRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.chat.messenger.images.SentImagesRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.chat.messenger.messeges.ReceivedMessagesRecyclerView
import ru.popkovden.messengerapplication.ui.adapters.chat.messenger.messeges.SentMessagesRecyclerView
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger

class FragmentMessengerScreen : Fragment() {

    private lateinit var binding: FragmentMessengerScreenBinding
    private val uiHelper: StatusBarColorChanger by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_messenger_screen, container, false)

        uiHelper.changeStatusBarColor(requireActivity(), R.color.whiteColor)

        val mergeAdapter = MergeAdapter(
            ReceivedMessagesRecyclerView(requireContext()),
            SentMessagesRecyclerView(requireContext()),
            SentImagesRecyclerView(requireContext()),
            ReceivedImagedRecyclerView(requireContext()),
            ReceivedVoiceMessagesRecyclerView(requireContext()),
            SentVoiceMessagesRecyclerView(requireContext())
        )

        binding.messengerScreenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messengerScreenRecyclerView.adapter = mergeAdapter

        binding.messengerToolbar.backToContactList.setOnClickListener {
            val action = FragmentMessengerScreenDirections.actionFragmentMessengerScreenToChat()
            findNavController().navigate(action)
        }

        return binding.root
    }
}