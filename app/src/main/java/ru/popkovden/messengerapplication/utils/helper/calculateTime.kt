package ru.popkovden.messengerapplication.utils.helper

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

private fun currentDate(): Date {
    val calendar = Calendar.getInstance()
    return calendar.time
}

fun getTimeAgo(date: Date, minute: String, hour: String, day: String): String {
    var time = date.time
    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = currentDate().time
    if (time > now || time <= 0) {
        return "in the future"
    }

    val diff = now - time
    return when {
        diff < MINUTE_MILLIS -> " · 10s"
        diff < 2 * MINUTE_MILLIS -> " · 1$minute"
        diff < 60 * MINUTE_MILLIS -> " · ${diff / MINUTE_MILLIS}$minute"
        diff < 2 * HOUR_MILLIS -> " · 1$hour"
        diff < 24 * HOUR_MILLIS -> " · ${diff / HOUR_MILLIS}$hour"
        diff < 48 * HOUR_MILLIS -> " · 1$day"
        else -> " · ${diff / DAY_MILLIS}$day"
    }
}

fun calculateTime(timeSend: String, minute: String, hour: String, day: String, daySend: String): String {

    var currentCalculateResult = ""

    try {

        val sdf = SimpleDateFormat("dd-M-yyyy hh:mm")
        val dateString = "$daySend $timeSend"
        val parseDate = sdf.parse(dateString)

        currentCalculateResult = getTimeAgo(parseDate!!, minute, hour, day)
    } catch (e: Exception) {
        Log.d("efefe", "$e - error")
    }

    return currentCalculateResult
}