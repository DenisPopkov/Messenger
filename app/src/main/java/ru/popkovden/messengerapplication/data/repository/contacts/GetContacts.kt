package ru.popkovden.messengerapplication.data.repository.contacts

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.popkovden.messengerapplication.model.ContactFriendModel
import ru.popkovden.messengerapplication.utils.helper.getData.getLastMessage

object GetContacts {

    suspend fun getContacts(UID: String, minute: String, hour: String, day: String): ArrayList<ContactFriendModel> {

        val contacts: ArrayList<ContactFriendModel> = arrayListOf()

        val firestoreReference = FirebaseFirestore.getInstance()
            .collection("users").document(UID).collection("contacts").get().await()

        for (contact in firestoreReference.documents) {

            val lastMessageHashMap = getLastMessage(UID, contact["contactUID"].toString(), minute, hour, day)

            contacts.add(ContactFriendModel(
                contact["contactName"].toString(),
                contact["contactUID"].toString(),
                contact["contactPhoto"].toString(),
                lastMessageHashMap
            ))
        }

        return contacts
    }
}