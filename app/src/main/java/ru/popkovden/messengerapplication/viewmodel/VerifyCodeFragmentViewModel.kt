package ru.popkovden.messengerapplication.viewmodel

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val TAG = "MessengerTag"

class VerifyCodeFragmentViewModel() : ViewModel() {

    private val liveDataTimerCodeResend = MutableLiveData<Int>()
    val currentLiveDataTimerCodeResend: LiveData<Int>
        get() = liveDataTimerCodeResend

    init {

        liveDataTimerCodeResend.value = 60

        CoroutineScope(Main).launch {
            for (i in 59 downTo 0) {
                delay(1000)
                liveDataTimerCodeResend.value = currentLiveDataTimerCodeResend.value?.minus(1)
            }
        }
    }

    fun verifyCode() {
        Log.d("efefe", "test")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MessengerTag", "VerifyCodeFragmentViewModel cleared")
    }
}