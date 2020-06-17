package ru.popkovden.messengerapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SendPhoneNumberFragmentViewModel : ViewModel() {

    private val phoneNumber = MutableLiveData<String>()
    val currentPhoneNumber: LiveData<String>
        get() = phoneNumber

    init {

    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MessengerTag", "SendPhoneNumberFragment cleared")
    }
}