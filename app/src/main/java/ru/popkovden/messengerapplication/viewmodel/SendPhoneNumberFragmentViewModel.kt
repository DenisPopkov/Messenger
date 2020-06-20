package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SendPhoneNumberFragmentViewModel : ViewModel() {

    private val phoneNumber = MutableLiveData<String>()
    val currentPhoneNumber: LiveData<String>
        get() = phoneNumber

    fun update(result : String) {
        phoneNumber.value = result
    }
}