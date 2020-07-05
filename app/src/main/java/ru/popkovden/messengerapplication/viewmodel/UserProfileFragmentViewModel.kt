package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.popkovden.messengerapplication.model.PostsModel

class UserProfileFragmentViewModel : ViewModel() {

    private val postsLiveData =  MutableLiveData<MutableList<PostsModel>>()
    val currentPostsLiveData: LiveData<MutableList<PostsModel>>
        get() = postsLiveData

//    suspend fun getPosts(UID: String) {
//        postsLiveData.postValue(GetPosts.getPosts(UID))
//    }
}