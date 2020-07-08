package ru.popkovden.messengerapplication.model

data class SendMessageModel(
    val message: String,
    val time: String,
    val uidSender: String,
    val id: String
)