package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers.IO
import ru.popkovden.messengerapplication.data.repository.contacts.GetContacts
import ru.popkovden.messengerapplication.utils.helper.Event

class ChatScreenFragmentViewModel(val UID: String) : ViewModel() {

    fun getContacts() = liveData(IO) {

        emit(Event.loading(data = null))

        try {
            emit(Event.success(data = GetContacts.getContacts(UID)))
        } catch (exception: Exception) {
            emit(Event.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}