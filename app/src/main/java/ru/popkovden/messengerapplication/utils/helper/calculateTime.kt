package ru.popkovden.messengerapplication.utils.helper

import android.util.Log
import ru.popkovden.messengerapplication.utils.helper.getData.getCurrentDateTime
import ru.popkovden.messengerapplication.utils.helper.getData.toString
import kotlin.math.abs

fun calculateTime(timeSend: String, minute: String, hour: String): String {

    val currentHour = getCurrentDateTime().toString("HH").toInt()
    val currentMinute = getCurrentDateTime().toString("mm").toInt()

    var currentCalculateResult = ""

    try {
        val calculateHour: Int? = timeSend.substring(0..1).toInt() - currentHour
        val calculateMinute: Int? = timeSend.substring(3..4).toInt() - currentMinute

        currentCalculateResult = if (calculateHour == 0) {
            "· " + abs(calculateMinute!!).toString() + minute
        } else {
            "· " + abs(calculateHour!!).toString() + hour
        }

    } catch (e: Exception) {
        Log.d("efefe", "$e - error")
    }

    return currentCalculateResult
}