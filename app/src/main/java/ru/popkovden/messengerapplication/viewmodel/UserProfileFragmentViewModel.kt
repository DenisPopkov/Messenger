package ru.popkovden.messengerapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.popkovden.messengerapplication.data.repository.posts.GetPosts
import ru.popkovden.messengerapplication.utils.helper.Event

class UserProfileFragmentViewModel(val UID: String, val context: Context, val image: String, val name: String) : ViewModel() {

    fun getPosts() = liveData(Dispatchers.IO) {

        emit(Event.loading(data = null))

        try {
            emit(Event.success(data = GetPosts.getPosts(UID, context, image, name).sortedWith(compareBy { it.id })))
        } catch (exception: Exception) {
            emit(Event.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}