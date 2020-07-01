package ru.popkovden.messengerapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GreetingFragmentViewModel : ViewModel() {

    private val uriLiveData =  MutableLiveData<Uri>()
    val currentUriLiveData: LiveData<Uri>
        get() = uriLiveData

    fun updateUri(uri: Uri) {
        uriLiveData.value = uri
    }
}