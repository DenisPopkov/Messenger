package ru.popkovden.messengerapplication.model

data class MessageModel(
    val sentMessages: ArrayList<HashMap<String, Any>>?,
    val receivedMessages: ArrayList<HashMap<String, Any>>?,
    val sentImages: ArrayList<HashMap<String, Any>>?,
    val receivedImages: ArrayList<HashMap<String, Any>>?,
    val CONTENT_TYPE: Int = 0,
    val id: Int = 0
) {
    constructor(): this(arrayListOf<HashMap<String, Any>>(), arrayListOf<HashMap<String, Any>>(), arrayListOf<HashMap<String, Any>>(), arrayListOf<HashMap<String, Any>>(),0, 0)
}