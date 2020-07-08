package ru.popkovden.messengerapplication.model

data class PostsModel(
    val postImages: ArrayList<String>?,
    val postVideos: ArrayList<String>?,
    var likeCount: String,
    var postTitle: String,
    var postMainText: String,
    var id: String
) {
    constructor(): this(arrayListOf<String>(), arrayListOf<String>(),"", "", "", "")
}