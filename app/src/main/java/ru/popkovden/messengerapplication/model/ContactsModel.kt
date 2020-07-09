package ru.popkovden.messengerapplication.model

data class ContactsModel(
    var contactName: String = "",
    var contactPhoneNumber: String = "",
    var contactId: Long = 0L,
    var contactPhoto: String? = ""
)