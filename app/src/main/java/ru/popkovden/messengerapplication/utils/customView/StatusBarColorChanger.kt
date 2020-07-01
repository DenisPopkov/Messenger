package ru.popkovden.messengerapplication.utils.customView

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import ru.popkovden.messengerapplication.R

object StatusBarColorChanger {

    fun changeStatusBarColor(activity: Activity, color: Int) {
        val window = activity.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(activity, color)
        } else {
            window.statusBarColor = ContextCompat.getColor(activity, R.color.grayColor)
        }
    }
}