package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessengerFragmentViewModel : ViewModel() {

    private val messagesLiveData = MutableLiveData<ArrayList<HashMap<String, Any>>>()
    val currentMessagesLiveData: LiveData<ArrayList<HashMap<String, Any>>>
        get() = messagesLiveData

    fun getSentMessages(message: ArrayList<HashMap<String, Any>>) {
        messagesLiveData.value?.clear()
        messagesLiveData.postValue(message)
    }
}