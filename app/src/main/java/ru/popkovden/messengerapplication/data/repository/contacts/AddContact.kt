package ru.popkovden.messengerapplication.data.repository.contacts

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.ContactFriendModel

object AddContact {

    private val firestoreReference = FirebaseFirestore.getInstance()
        .collection("users")

    fun addContact(contact: ContactFriendModel, UID: String) = CoroutineScope(IO).launch {

        val contactInfo = hashMapOf<String, String>()

        contactInfo["contactName"] = contact.contactName
        contactInfo["contactUID"] = contact.contactUID
        contactInfo["contactPhoto"] = contact.contactPhoto

        firestoreReference.document(UID).collection("contacts").document(contact.contactUID).set(contactInfo)
        firestoreReference.document(contact.contactUID).collection("contacts").document(UID).set(contactInfo)
    }
}