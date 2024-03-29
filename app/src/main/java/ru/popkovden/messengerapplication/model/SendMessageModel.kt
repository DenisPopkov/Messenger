package ru.popkovden.messengerapplication.model

data class SendMessageModel(
    val message: String,
    val time: String,
    val uidSender: String,
    val id: Int,
    val wasRead: String,
    val documentId: String,
    val CONTENT_TYPE: Int
) {
    constructor(): this("", "", "", 0, "", "",0)
}