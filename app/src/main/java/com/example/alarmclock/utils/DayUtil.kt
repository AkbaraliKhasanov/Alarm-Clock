package com.example.alarmclock.utils

import java.util.*

object DayUtil {
    @Throws(Exception::class)
    fun toDay(day: Int): String {
        when (day) {
            Calendar.SUNDAY -> return "Yakshanba"
            Calendar.MONDAY -> return "Dushanba"
            Calendar.TUESDAY -> return "Seshanba"
            Calendar.WEDNESDAY -> return "Chorshanba"
            Calendar.THURSDAY -> return "Payshanba"
            Calendar.FRIDAY -> return "Juma"
            Calendar.SATURDAY -> return "Shanba"
        }
        throw Exception("Kun topilmadi.")
    }
}
