package ru.popkovden.messengerapplication.data.repository.auth

import android.content.Context
import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

object CreateUser {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val storageReference = firebaseStorage.reference.child("userAvatars")
    private val userInfo = hashMapOf<String, Any>()

    fun loadImageToDatabase(userImage: Uri, UID: String, infoProfileImage: InfoAboutUser) {

        var image = ""

        storageReference.child(UID).putFile(userImage).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl

            result?.addOnSuccessListener { uri ->
                image = uri.toString()
            }?.addOnCompleteListener {
                infoProfileImage.userProfileImage = image
            }
        }
    }

    fun userCreate(context: Context, userInfo: HashMap<String, String>, infoAboutUser: InfoAboutUser, UID: String) {

        // Добовляет все данные о пользователе
        firebaseFirestore.collection("users").document(UID).set(userInfo)
        infoAboutUser.saveInfo(context)
    }

    fun updateUserInfo(userName: String, context: Context, infoAboutUser: InfoAboutUser) {

        userInfo["userName"] = userName
        userInfo["UID"] = infoAboutUser.UID
        infoAboutUser.userName = userName

        storageReference.child(infoAboutUser.UID).downloadUrl.addOnSuccessListener { task ->
            userInfo["userProfileImage"] = task.toString()

            // Добовляет все данные о пользователе
            firebaseFirestore.collection("users").document(infoAboutUser.UID)
                .update(userInfo)
        }

        infoAboutUser.saveInfo(context)
    }
}