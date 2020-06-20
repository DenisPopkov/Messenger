package ru.popkovden.messengerapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VerifyCodeFragmentViewModel : ViewModel() {

    private val liveDataTimerCodeResend = MutableLiveData<Int>()
    val currentLiveDataTimerCodeResend: LiveData<Int>
        get() = liveDataTimerCodeResend

    init {
        liveDataTimerCodeResend.value = 60
        timerRun()
    }

    fun timerReset() {
        liveDataTimerCodeResend.value = 60
        timerReset()
    }

    private fun timerRun() {
        viewModelScope.launch {
            for (i in 59 downTo 0) { // Обратный отсчет до повторной отправки кода
                delay(1000)
                liveDataTimerCodeResend.value = currentLiveDataTimerCodeResend.value?.minus(1)
            }
        }
    }
}