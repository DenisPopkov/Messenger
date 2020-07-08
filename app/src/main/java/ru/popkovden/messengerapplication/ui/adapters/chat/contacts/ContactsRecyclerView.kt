package ru.popkovden.messengerapplication.ui.adapters.chat.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contacts_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.auth.CheckIfUserExist
import ru.popkovden.messengerapplication.model.ContactsModel

class ContactsRecyclerView(private val contactsList: MutableList<ContactsModel>) : RecyclerView.Adapter<ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder = ContactsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.contacts_item, parent, false))

    override fun getItemCount(): Int = contactsList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) = holder.itemView.run {

        val currentContactsItem = contactsList[position]

        contactName.text = currentContactsItem.contactName
        contactNumber.text = currentContactsItem.contactPhoneNumber

        this.setOnClickListener {
            it.setOnClickListener {
                CoroutineScope(IO).launch {
                    val checkResult = CheckIfUserExist.check(currentContactsItem.contactPhoneNumber)
                    if (checkResult) {
                        withContext(Main) {
                            Toast.makeText(context, "Успешно", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        withContext(Main) {
                            Toast.makeText(context, resources.getString(R.string.contact_not_exist), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)