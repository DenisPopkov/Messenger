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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.contacts.GetContacts
import ru.popkovden.messengerapplication.databinding.FragmentChatScreenBinding
import ru.popkovden.messengerapplication.model.ContactFriendModel
import ru.popkovden.messengerapplication.ui.adapters.chat.contacts.UserMessagesRecyclerView
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class ChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentChatScreenBinding
    private val getContactsHelper: GetContacts by inject()
    private val infoAboutUser: InfoAboutUser by inject()
    private var contactsList = arrayListOf<ContactFriendModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_screen, container, false)

        // Получение информации о профиле пользователя
        infoAboutUser.loadInfoFromSharedPreferences(requireContext())
        val uid = infoAboutUser.UID

        binding.chatScreenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        CoroutineScope(Main).launch {
            binding.chatScreenRecyclerView.adapter = UserMessagesRecyclerView(requireContext(), getContactsHelper.getContacts(uid))
        }

        binding.chatToolbar.attachFile.setOnClickListener {
            findNavController().navigate(ChatScreenFragmentDirections.actionChatToContactsFragment())
        }

        return binding.root
    }
}