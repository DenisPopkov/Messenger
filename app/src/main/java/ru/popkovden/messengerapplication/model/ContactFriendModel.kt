package ru.popkovden.messengerapplication.model

data class ContactFriendModel(
    val contactName: String,
    val contactUID: String,
    val contactPhoto: String,
    val lastMessage: HashMap<String, String>
)