package ru.popkovden.messengerapplication.model

data class LastMessageModel(
    val daySend: String,
    val timeSend: String,
    val lastMessage: String,
    val uidSender: String,
    var wasRead: String
) {
    constructor(): this ("", "", "", "", "")
}