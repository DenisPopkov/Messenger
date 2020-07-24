package ru.popkovden.messengerapplication.data.repository.contacts

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import ru.popkovden.messengerapplication.model.ContactFriendModel
import ru.popkovden.messengerapplication.utils.helper.getData.getCurrentDateTime
import ru.popkovden.messengerapplication.utils.helper.getData.setLastMessage
import ru.popkovden.messengerapplication.utils.helper.getData.toString
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

object AddContact {

    private val firestoreReference = FirebaseFirestore.getInstance()
        .collection("users")

    fun addContact(contact: ContactFriendModel, UID: String, userName: String) = CoroutineScope(IO).launch {

        val contactInfo = hashMapOf<String, String>()

        contactInfo["contactName"] = userName
        contactInfo["contactUID"] = contact.contactUID
        contactInfo["contactPhoto"] = contact.contactPhoto
        setLastMessage(UID, contact.contactUID, "", "", getCurrentDateTime().toString("dd-M-yyyy"), "false")

        val contactInfo2 = hashMapOf<String, String>()

        contactInfo2["contactName"] = InfoAboutUser.userName
        contactInfo2["contactUID"] = InfoAboutUser.UID
        contactInfo2["contactPhoto"] = InfoAboutUser.userProfileImage
        setLastMessage(contact.contactUID, UID, "", "", getCurrentDateTime().toString("dd-M-yyyy"), "false")

        firestoreReference.document(contact.contactUID).collection("contacts").document(UID).set(contactInfo2) // добовляет контакт другому
        firestoreReference.document(UID).collection("contacts").document(contact.contactUID).set(contactInfo) // добовляет контакт себе

        val hashMap2 = hashMapOf("collectionSize" to 0)

        FirebaseFirestore.getInstance().collection("users")
            .document(UID).collection("chats").document(contact.contactUID).collection("Size").document("CollectionSize").set(hashMap2)

        FirebaseFirestore.getInstance().collection("users")
            .document(contact.contactUID).collection("chats").document(UID).collection("Size").document("CollectionSize").set(hashMap2)
    }
}