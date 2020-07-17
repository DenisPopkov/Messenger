package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.popkovden.messengerapplication.data.repository.posts.GetPosts
import ru.popkovden.messengerapplication.utils.helper.Event

class UserProfileFragmentViewModel(val UID: String) : ViewModel() {

    fun getPosts() = liveData(Dispatchers.IO) {

        emit(Event.loading(data = null))

        try {
            emit(Event.success(data = GetPosts.getPosts(UID).data?.sortedWith(compareBy { it.id })))
        } catch (exception: Exception) {
            emit(Event.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}