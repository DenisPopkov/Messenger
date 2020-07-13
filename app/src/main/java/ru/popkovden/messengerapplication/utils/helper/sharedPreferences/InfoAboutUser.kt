package ru.popkovden.messengerapplication.utils.helper.sharedPreferences

import android.content.Context

object InfoAboutUser {

    var userName = ""
    var userProfileImage = ""
    var UID = ""
    var phoneNumber = ""

    fun saveInfo(context: Context) {
        val sharedPreference =  context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("UID", UID)
        editor.putString("userName", userName)
        editor.putString("userProfileImage", userProfileImage)
        editor.putString("phoneNumber", phoneNumber)
        editor.apply()
    }

    fun loadInfoFromSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        UID = sharedPreferences.getString("UID", "").toString()
        userName = sharedPreferences.getString("userName", "").toString()
        userProfileImage = sharedPreferences.getString("userProfileImage", "").toString()
        phoneNumber = sharedPreferences.getString("phoneNumber", "").toString()
    }
}