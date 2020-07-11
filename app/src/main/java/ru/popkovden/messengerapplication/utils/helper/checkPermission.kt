package ru.popkovden.messengerapplication.utils.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/* Файл для работы с разрешениями*/

const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
const val PERMISSION_REQUEST = 200

fun checkPermission(permission: String, activity: Activity): Boolean {
    /* Функция принимает разрешение и проверяет, если разрешение еще не было
    * предоставлено запускает окно с запросом пользователю */
    return if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), PERMISSION_REQUEST)
        false
    } else true
}