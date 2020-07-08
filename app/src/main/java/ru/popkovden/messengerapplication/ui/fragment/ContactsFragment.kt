package ru.popkovden.messengerapplication.ui.fragment

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.contacts_toolbar.view.*
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.databinding.FragmentContactsBinding
import ru.popkovden.messengerapplication.model.ContactsModel
import ru.popkovden.messengerapplication.ui.adapters.chat.contacts.ContactsRecyclerView
import ru.popkovden.messengerapplication.utils.helper.READ_CONTACTS
import ru.popkovden.messengerapplication.utils.helper.checkPermission


class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts, container, false)

        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.contactsRecyclerView.adapter = ContactsRecyclerView(initContacts())

        binding.contactsToolbar.backToChatMessages.setOnClickListener {
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToChat())
        }

        return binding.root
    }

    // Получает контакты из телефонной книги
    private fun initContacts(): MutableList<ContactsModel> {

        val arrayContacts = arrayListOf<ContactsModel>()

        if (checkPermission(READ_CONTACTS, requireActivity())) {
            val cursor = requireActivity().contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            cursor?.let {
                while (it.moveToNext()){
                    // Получает имя контакта и номер телефона из телефонной книги
                    val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    val regionalReplaceResult = phone.replace(Regex("^[8]"),"+7")

                    val newModel = ContactsModel()
                    newModel.contactName = fullName
                    newModel.contactPhoneNumber = regionalReplaceResult.replace(Regex("[\\s,-]"), "")
                    arrayContacts.add(newModel)
                }
            }
            cursor?.close()
        }

        return arrayContacts.toSet().toMutableList()
    }
}