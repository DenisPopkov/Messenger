package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.toolbar_for_messaging.view.*
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.messages.GetMessages
import ru.popkovden.messengerapplication.data.repository.messages.SendMessageToUser
import ru.popkovden.messengerapplication.databinding.FragmentMessengerScreenBinding
import ru.popkovden.messengerapplication.model.SendMessageModel
import ru.popkovden.messengerapplication.utils.customView.StatusBarColorChanger
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.viewmodel.MessengerFragmentViewModel

class FragmentMessengerScreen : Fragment() {

    private lateinit var binding: FragmentMessengerScreenBinding
    private val uiHelper: StatusBarColorChanger by inject()
    private val sendMessageHelper: SendMessageToUser by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    private val getSentMessagesHelper: GetMessages by inject()
    private val viewModel: MessengerFragmentViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger_screen, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiHelper.changeStatusBarColor(requireActivity(), R.color.whiteColor)

        // Настривает адапер
        val mergeAdapter = getSentMessagesHelper.getMessages("Kwe5CLjp9TeBs1OVfoCO4hqGRIu1", "12", binding.messengerScreenRecyclerView)
        binding.messengerScreenRecyclerView.adapter = mergeAdapter
        val adapter = binding.messengerScreenRecyclerView.adapter
//        binding.messengerScreenRecyclerView.smoothScrollToPosition(adapter!!.itemCount - 1)
        binding.messengerScreenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messengerScreenRecyclerView.setHasFixedSize(true)

        binding.messengerToolbar.backToContactList.setOnClickListener {
            val action = FragmentMessengerScreenDirections.actionFragmentMessengerScreenToChat()
            findNavController().navigate(action)
        }

        binding.bottomMessage.microphoneIcon.setOnClickListener {
            sendMessageHelper.sendMessage("Kwe5CLjp9TeBs1OVfoCO4hqGRIu1", "12", SendMessageModel(binding.bottomMessage.messageInput.text.toString(), "15:45", infoAboutUser.UID))
            binding.bottomMessage.messageInput.text?.clear()
        }
    }
}