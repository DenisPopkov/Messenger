package ru.popkovden.messengerapplication.model

data class SentImageModel(
    val image: ArrayList<String>,
    val time: String,
    val secretName: String
)