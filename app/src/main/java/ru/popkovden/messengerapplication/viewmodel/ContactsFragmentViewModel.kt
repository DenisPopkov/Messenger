package ru.popkovden.messengerapplication.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers.IO
import ru.popkovden.messengerapplication.utils.helper.Event
import ru.popkovden.messengerapplication.utils.helper.getData.initContacts

class ContactsFragmentViewModel(val activity: Activity) : ViewModel() {

    fun getContacts() = liveData(IO) {

        emit(Event.loading(data = null))

        try {
            emit(Event.success(data = initContacts(activity)))
        } catch (exception: Exception) {
            emit(Event.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}