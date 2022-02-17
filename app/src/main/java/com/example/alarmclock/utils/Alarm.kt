package com.example.alarmclock.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.FRIDAY
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.MONDAY
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.RECURRING
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.SATURDAY
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.SUNDAY
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.THURSDAY
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.TITLE
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.TUESDAY
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.VIBRATION
import com.example.alarmclock.broadcastreceiver.AlarmBroadcastReceiver.Companion.WEDNESDAY
import java.util.*

@Entity(tableName = "alarm_table")
class Alarm {
    @PrimaryKey
    var alarmId = 0
    var hour = 0
        private set
    var minute = 0
        private set
    var isStarted = false
        private set
    var isVibration = false
        private set
    var isRecurring = false
        private set
    var isMonday = false
        private set
    var isTuesday = false
        private set
    var isWednesday = false
        private set
    var isThursday = false
        private set
    var isFriday = false
        private set
    var isSaturday = false
        private set
    var isSunday = false
        private set
    var title: String? = null
        private set
    var created: Long = 0

    constructor(
        alarmId: Int,
        hour: Int,
        minute: Int,
        title: String?,
        created: Long,
        started: Boolean,
        recurring: Boolean,
        isVibration: Boolean,
        monday: Boolean,
        tuesday: Boolean,
        wednesday: Boolean,
        thursday: Boolean,
        friday: Boolean,
        saturday: Boolean,
        sunday: Boolean
    ) {
        this.alarmId = alarmId
        this.hour = hour
        this.minute = minute
        isStarted = started
        this.isVibration = isVibration
        isRecurring = recurring
        isMonday = monday
        isTuesday = tuesday
        isWednesday = wednesday
        isThursday = thursday
        isFriday = friday
        isSaturday = saturday
        isSunday = sunday
        this.title = title
        this.created = created
    }

    constructor(
        alarmId: Int,
        value: Int,
        value1: Int,
        desc: TextView?,
        created: Long,
        started: Boolean,
        recurring: Boolean,
        monday: Boolean?,
        tuesday: Boolean?,
        wednesday: Boolean?,
        thursday: Boolean?,
        friday: Boolean?,
        saturday: Boolean?,
        sunday: Boolean?
    ) {
    }

    @SuppressLint("DefaultLocale")
    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra(RECURRING, isRecurring)
        intent.putExtra(MONDAY, isMonday)
        intent.putExtra(TUESDAY, isTuesday)
        intent.putExtra(WEDNESDAY, isWednesday)
        intent.putExtra(THURSDAY, isThursday)
        intent.putExtra(FRIDAY, isFriday)
        intent.putExtra(SATURDAY, isSaturday)
        intent.putExtra(SUNDAY, isSunday)
        intent.putExtra(TITLE, title)
        intent.putExtra(VIBRATION, isVibration)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
        }
        if (!isRecurring) {
            var toastText: String? = null
            try {
                toastText = java.lang.String.format(
                    "Bir martalik signal rejalashtirilgan %s ga %02d:%02d", DayUtil.toDay(
                        calendar[Calendar.DAY_OF_WEEK]
                    ), hour, minute, alarmId
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        } else {
            val toastText = String.format(
                "Takrorlanuvchi signal rejalashtirilgan %s ga %02d:%02d",
                recurringDaysText, hour, minute, alarmId
            )
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                alarmPendingIntent
            )
        }
        isStarted = true
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
        isStarted = false
        val toastText =
            String.format("Signal bekor qilindi %02d:%02d", hour, minute)
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }

    val recurringDaysText: String?
        get() {
            if (!isRecurring) {
                return null
            }
            var days = ""
            if (isMonday) {
                days += "Dush "
            }
            if (isTuesday) {
                days += "Sesh "
            }
            if (isWednesday) {
                days += "Chor "
            }
            if (isThursday) {
                days += "Pay "
            }
            if (isFriday) {
                days += "Jum "
            }
            if (isSaturday) {
                days += "Shan "
            }
            if (isSunday) {
                days += "Yak "
            }
            return days
        }
}
