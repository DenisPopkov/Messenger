package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_toolbar_for_chat_screen.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentChatScreenBinding
import ru.popkovden.messengerapplication.ui.adapters.chat.contacts.UserMessagesRecyclerView
import ru.popkovden.messengerapplication.utils.helper.Status
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.viewmodel.ChatScreenFragmentViewModel


class ChatScreenFragment : Fragment() {

    private lateinit var binding: FragmentChatScreenBinding
    private val infoAboutUser: InfoAboutUser by inject()
    var uid = ""
    private val viewModel: ChatScreenFragmentViewModel by viewModel {
        parametersOf(
            uid,
            resources.getString(R.string.minute_abbreviation),
            resources.getString(R.string.hour_abbreviation),
            resources.getString(R.string.day_abbreviation)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_screen, container, false)

        // Получение информации о профиле пользователя
        infoAboutUser.loadInfoFromSharedPreferences(requireContext())
        uid = infoAboutUser.UID

        // Настройка адаптера, получение контактов
        binding.chatScreenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getContacts().observe(viewLifecycleOwner, Observer {

            when (it.status) {

                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                Status.ERROR -> Log.d("efefe", "error")
                Status.SUCCESS -> {
                    it?.data?.let { result ->
                        binding.progress.visibility = View.GONE
                        binding.chatScreenRecyclerView.adapter =
                            UserMessagesRecyclerView(requireContext(), result, uid)
//                        instanceDB()
                    }
                }
            }
        })

        binding.chatToolbar.attachFile.setOnClickListener {
            findNavController().navigate(ChatScreenFragmentDirections.actionChatToContactsFragment())
        }

        return binding.root
    }

//    fun instanceDB() {
//        CoroutineScope(IO).launch {
//            val db = DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
//
//            val contactsEntities = ContactsEntities(1, "1", "1")
//            db.insertContacts(contactsEntities)
//        }
//    }
}