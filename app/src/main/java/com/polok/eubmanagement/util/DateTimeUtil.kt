package com.polok.eubmanagement.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object DateTimeUtil {

    fun getFormattedCurrentDate(datePattern: DatePattern): String = getFormattedDateTimeInBDT(
        datePattern.pattern
    )

    fun getFormattedCurrentTime(timePattern: TimePattern): String = getFormattedDateTimeInBDT(
        timePattern.pattern
    )

    private fun getFormattedDateTimeInBDT(pattern: String?): String = try {
        SimpleDateFormat(
            pattern, Locale.ENGLISH
        ).apply {
            timeZone = TimeZone.getTimeZone("Asia/Dhaka")
        }.format(
            Calendar.getInstance().time
        ) ?: ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

enum class DatePattern(val pattern: String) {
    SUCCESS_FAIL_SCREEN_DATE("dd MMM, yyyy")
}

enum class TimePattern(val pattern: String) {
    SUCCESS_FAIL_SCREEN_TIME("hh:mm aa")
}