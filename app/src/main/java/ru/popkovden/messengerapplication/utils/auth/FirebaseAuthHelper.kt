package ru.popkovden.messengerapplication.utils.auth

import android.util.Log
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.*
import java.util.concurrent.TimeUnit

object FirebaseAuthHelper {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun sendSmsCode(phoneNumber: String) : HashMap<String, String>{

        firebaseAuth.setLanguageCode(Locale.getDefault().language)

        val infoFromSmsCode = hashMapOf<String, String>()

        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(token: PhoneAuthCredential) {

                val code = token.smsCode.toString()
                infoFromSmsCode["smsCode"] = code
            }

            override fun onVerificationFailed(error: FirebaseException) {
                Log.d("Messenger", "sendSmsCode error - $error")
            }

            override fun onCodeSent(id: String, resendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, resendingToken)

                infoFromSmsCode["verificationId"] = id
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,
            60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, callback) // Отправка смс с кодом

        return infoFromSmsCode
    }

    fun signIn(verificationId: String, verificationCode: String){
        try {
            val credintal = PhoneAuthProvider.getCredential(verificationId, verificationCode)
            firebaseAuth.signInWithCredential(credintal)
        } catch (error: Exception) {
            Log.d("Messenger", "signIn error - $error")
        }
    }
}