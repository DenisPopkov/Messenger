package ru.popkovden.messengerapplication.data.repository.auth

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.ext.android.inject
import ru.popkovden.messengerapplication.utils.helper.sharedPreferences.InfoAboutUser

class CreateUser : AppCompatActivity() {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val storageReference = firebaseStorage.reference.child("userAvatars")
    private val userInfo = hashMapOf<String, Any>()
    private val infoAboutUser: InfoAboutUser by inject()

    fun loadImageToDatabase(userImage: Uri) {
        storageReference.child(infoAboutUser.UID).putFile(userImage)
        infoAboutUser.userProfileImage = userImage.toString()
    }

    fun userCreate(phoneNumber: String, userName: String, context: Context) {

        userInfo["userName"] = userName
        userInfo["phoneNumber"] = phoneNumber
        userInfo["UID"] = infoAboutUser.UID
        infoAboutUser.userName = userName
        infoAboutUser.phoneNumber = phoneNumber

        storageReference.child(infoAboutUser.UID).downloadUrl.addOnSuccessListener { task ->
            userInfo["userProfileImage"] = task.toString()

            // Добовляет все данные о пользователе
            firebaseFirestore.collection("users").document(infoAboutUser.UID)
                .set(userInfo)
        }

        infoAboutUser.saveInfo(context)
    }

    fun updateUserInfo(userName: String, context: Context) {

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