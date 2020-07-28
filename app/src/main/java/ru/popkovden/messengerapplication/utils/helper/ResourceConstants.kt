package ru.popkovden.messengerapplication.utils.helper

import android.content.res.Resources
import ru.popkovden.messengerapplication.R

class ResourceConstants {

    companion object {
        val day = Resources.getSystem().getString(R.string.day_abbreviation)
        val hour = Resources.getSystem().getString(R.string.hour_abbreviation)
        val minute = Resources.getSystem().getString(R.string.minute_abbreviation)
    }
}