package ru.popkovden.messengerapplication.data.repository.auth

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.appcompat.widget.AppCompatImageButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

object CreateUser {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val storageReference = firebaseStorage.reference.child("userAvatars")
    private val userInfo = hashMapOf<String, Any>()

    fun loadImageToDatabase(userImage: Uri, UID: String, nextButton: AppCompatImageButton) {

        var image = ""

        storageReference.child(UID).putFile(userImage).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl

            result?.addOnSuccessListener { uri ->
                Log.d("efefe", "uri - $uri")
                image = uri.toString()
                nextButton.isEnabled = false
                nextButton.isClickable = false
            }?.addOnCompleteListener {
                Log.d("efefe", "image - $image")
                InfoAboutUser.userProfileImage = image
                nextButton.isEnabled = true
                nextButton.isClickable = true
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
        userInfo["userProfileImage"] = infoAboutUser.userProfileImage
        infoAboutUser.userName = userName

        // Добовляет все данные о пользователе
        firebaseFirestore.collection("users").document(infoAboutUser.UID)
            .update(userInfo)

        infoAboutUser.saveInfo(context)
    }
}