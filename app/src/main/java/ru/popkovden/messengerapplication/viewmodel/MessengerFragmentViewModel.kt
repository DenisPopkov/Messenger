package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessengerFragmentViewModel : ViewModel() {

    private val imagesLiveData = MutableLiveData<ArrayList<String>>()
    val currentImagesLiveData: LiveData<ArrayList<String>>
        get() = imagesLiveData

    fun addAll(imagesList: ArrayList<String>) {
        imagesLiveData.postValue(imagesList)
    }
}