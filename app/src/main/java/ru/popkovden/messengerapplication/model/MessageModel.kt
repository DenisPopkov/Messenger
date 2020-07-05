package ru.popkovden.messengerapplication.model

data class MessageModel(
    val sentMessages: ArrayList<HashMap<String, Any>>,
    val receivedMessages: ArrayList<HashMap<String, Any>>
) {
    constructor(): this(arrayListOf<HashMap<String, Any>>(), arrayListOf<HashMap<String, Any>>())
}