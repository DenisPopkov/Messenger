package ru.popkovden.messengerapplication.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateUser {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val storageReference = firebaseStorage.reference.child("userAvatars")
    private val userInfo = hashMapOf<String, Any>()

    fun userCreate(userImage: Uri, phoneNumber: String, userName: String, UID: String) {

        //Добавляет фото профиля в БД
        storageReference.child(UID).putFile(userImage)

        userInfo["userName"] = userName
        userInfo["phoneNumber"] = phoneNumber
        userInfo["UID"] = UID

        CoroutineScope(IO).launch {
            delay(2000)

            storageReference.child(UID).downloadUrl.addOnSuccessListener { task ->
                Log.d("efefe", task.toString())
                userInfo["userProfileImage"] = task.toString()
            }

            // Добовляет все данные о пользователе
            firebaseFirestore.collection("users").document()
                .set(userInfo)
        }
    }
}