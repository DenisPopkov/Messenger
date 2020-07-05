package ru.popkovden.messengerapplication.model

data class PostsModel(
    val postImages: ArrayList<String>?,
    val postVideos: ArrayList<String>?,
    val postFiles: ArrayList<String>?,
    var likeCount: String,
    var postTitle: String,
    var postMainText: String
) {
    constructor(): this(arrayListOf<String>(), arrayListOf<String>(), arrayListOf<String>(),"", "", "")
}