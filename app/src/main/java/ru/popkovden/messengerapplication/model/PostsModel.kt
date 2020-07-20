package ru.popkovden.messengerapplication.model

data class PostsModel(
    val postImages: ArrayList<String>?,
    val postVideos: ArrayList<String>?,
    var likeCount: String,
    var postTitle: String,
    var postMainText: String,
    var uidSender: String,
    var photoProfile: String,
    var postName: String,
    var timeSend: String,
    var id: Long
) {
    constructor(): this(arrayListOf<String>(), arrayListOf<String>(),"", "", "","","", "","", 0)
}