package ru.popkovden.messengerapplication.utils.helper

import android.app.Activity
import android.provider.ContactsContract
import ru.popkovden.messengerapplication.model.ContactsModel

fun initContacts(activity: Activity): List<ContactsModel> {

    val arrayContacts = arrayListOf<ContactsModel>()

    if (checkPermission(READ_CONTACTS, activity)) {
        val cursor = activity.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)

        cursor?.let {
            while (it.moveToNext()){

                // Получает имя контакта и номер телефона из телефонной книги
                val fullName = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val id = it.getLong(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                val contactPhoto = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                // Переводит номер в международный формат России
                val regionalReplaceResult = phone.replace(Regex("^[8]"),"+7")

                // Заполняет данными список
                val newModel = ContactsModel()
                newModel.contactName = fullName
                newModel.contactPhoneNumber = regionalReplaceResult.replace(Regex("[\\s,-]"), "")
                newModel.contactId = id
                newModel.contactPhoto = contactPhoto
                arrayContacts.add(newModel)
            }
        }
        cursor?.close()
    }

    return arrayContacts.distinctBy {
        it.contactPhoneNumber
    }
}