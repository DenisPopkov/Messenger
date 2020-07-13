package ru.popkovden.messengerapplication.ui.adapters.chat.contacts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.contacts_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.popkovden.messengerapplication.R
import ru.popkovden.messengerapplication.data.repository.auth.CheckIfUserExist
import ru.popkovden.messengerapplication.data.repository.contacts.AddContact
import ru.popkovden.messengerapplication.model.ContactFriendModel
import ru.popkovden.messengerapplication.model.ContactsModel
import ru.popkovden.messengerapplication.ui.fragment.ContactsFragmentDirections
import ru.popkovden.messengerapplication.utils.helper.getUserImage
import ru.popkovden.messengerapplication.utils.helper.getUserName
import ru.popkovden.messengerapplication.utils.helper.getUserUID
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class ContactsRecyclerView(private val contactsList: List<ContactsModel>) : RecyclerView.Adapter<ContactsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder =
        ContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_item, parent, false)
        )

    override fun getItemCount(): Int = contactsList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) = holder.itemView.run {

        val currentContactsItem = contactsList[position]

        contactName.text = currentContactsItem.contactName
        contactNumber.text = currentContactsItem.contactPhoneNumber
        Glide.with(context).load(currentContactsItem.contactPhoto)
            .placeholder(R.drawable.contact_placeholder_icon_2).into(contactImage)

        this.setOnClickListener {
                CoroutineScope(IO).launch {

                    val checkResult = CheckIfUserExist.check(it.contactNumber.text.toString())
                    val userUID = getUserUID(currentContactsItem.contactPhoneNumber)
                    val image = getUserImage(currentContactsItem.contactPhoneNumber)

                    if (checkResult && image.isNotEmpty() && userUID.isNotEmpty()) {

                        val userPhoto = getUserImage(InfoAboutUser.phoneNumber)
                        val userName = getUserName(currentContactsItem.contactPhoneNumber)
                        Log.d("efefe", "$userPhoto photo")
                        Log.d("efefe", "$userName userNameeeee")

                        AddContact.addContact(ContactFriendModel(currentContactsItem.contactName, userUID, image), InfoAboutUser.UID, userPhoto, userName)
                        findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToMessenger(userUID, currentContactsItem.contactName, image))

                    } else {
                        withContext(Main) {
                            Toast.makeText(context, resources.getString(R.string.contact_not_exist), Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}

class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)