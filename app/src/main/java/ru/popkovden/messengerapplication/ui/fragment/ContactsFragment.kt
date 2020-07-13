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
import kotlinx.android.synthetic.main.contacts_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentContactsBinding
import ru.popkovden.messengerapplication.ui.adapters.chat.contacts.ContactsRecyclerView
import ru.popkovden.messengerapplication.utils.helper.Status
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser
import ru.popkovden.messengerapplication.viewmodel.ContactsFragmentViewModel

class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding
    private val viewModel: ContactsFragmentViewModel by viewModel { parametersOf(requireActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false)

        InfoAboutUser.loadInfoFromSharedPreferences(requireContext())
        Log.d("efefe", InfoAboutUser.phoneNumber)

        // Настройка адаптера
        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getContacts().observe(viewLifecycleOwner, Observer {
            when(it.status) {

                Status.LOADING -> Log.d("efefe", "loading")
                Status.ERROR -> Log.d("efefe", "error")
                Status.SUCCESS -> {
                    it?.data?.let { result ->
                        binding.contactsRecyclerView.adapter = ContactsRecyclerView(result)
                    }
                }
            }
        })

        binding.contactsToolbar.backToChatMessages.setOnClickListener {
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToChat())
        }

        return binding.root
    }
}