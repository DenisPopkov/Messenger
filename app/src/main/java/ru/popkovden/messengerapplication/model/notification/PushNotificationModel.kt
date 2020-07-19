package ru.popkovden.messengerapplication.model.notification

data class PushNotificationModel(
    val data: NotificationData?,
    val to: String
)