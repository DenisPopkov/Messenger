package ru.popkovden.messengerapplication.utils.darkMode

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

fun enableDarkMode(context: Context) {

    var darkModeEnable = loadInfoAboutMode(context)

    when (darkModeEnable) {
        false -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            darkModeEnable = true
            saveMode(context, darkModeEnable)
        }
        true -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            darkModeEnable = false
            saveMode(context, darkModeEnable)
        }
    }
}

fun openDarkMode(context: Context) {

    when (loadInfoAboutMode(context)) {
        true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}

private fun saveMode(context: Context, darkModeEnable: Boolean) {
    val sharedPreference = context.getSharedPreferences("darkMode", Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    editor.putBoolean("mode", darkModeEnable)
    editor.apply()
}

fun loadInfoAboutMode(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("darkMode", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("mode", false)
}