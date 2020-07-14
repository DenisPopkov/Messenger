package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserProfileFragmentViewModel : ViewModel() {

    private val translationLiveData =  MutableLiveData<Float>()
    val currentTranslationLiveData: LiveData<Float>
        get() = translationLiveData


    fun updateTranslation(value: Float) {
        translationLiveData.value = value
    }
}