package ru.popkovden.messengerapplication.model.notification

data class NotificationData(
    val title: String,
    val message: String,
    val image: String,
    val userUID: String,
    val screenStatus: String
)