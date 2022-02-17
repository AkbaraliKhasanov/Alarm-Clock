package com.example.alarmclock.broadcastreceiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.alarmclock.service.AlarmService
import com.example.alarmclock.service.RescheduleAlarmsService
import java.util.*


class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            startRescheduleAlarmsService(context)
        } else {
            if (!intent.getBooleanExtra(RECURRING, false)) {
                startAlarmService(context, intent)
            }
            run {
                if (alarmIsToday(intent)) {
                    startAlarmService(context, intent)
                }
            }
        }
    }

    private fun alarmIsToday(intent: Intent): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        when (calendar[Calendar.DAY_OF_WEEK]) {
            Calendar.MONDAY -> return intent.getBooleanExtra(MONDAY, false)
            Calendar.TUESDAY -> return intent.getBooleanExtra(TUESDAY, false)
            Calendar.WEDNESDAY -> return intent.getBooleanExtra(WEDNESDAY, false)
            Calendar.THURSDAY -> return intent.getBooleanExtra(THURSDAY, false)
            Calendar.FRIDAY -> return intent.getBooleanExtra(FRIDAY, false)
            Calendar.SATURDAY -> return intent.getBooleanExtra(SATURDAY, false)
            Calendar.SUNDAY -> return intent.getBooleanExtra(SUNDAY, false)
        }
        return false
    }

    private fun startAlarmService(context: Context, intent: Intent) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE))
        intentService.putExtra(VIBRATION, intent.getBooleanExtra(VIBRATION, true))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmsService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    companion object {
        const val MONDAY = "MONDAY"
        const val TUESDAY = "TUESDAY"
        const val WEDNESDAY = "WEDNESDAY"
        const val THURSDAY = "THURSDAY"
        const val FRIDAY = "FRIDAY"
        const val SATURDAY = "SATURDAY"
        const val SUNDAY = "SUNDAY"
        const val RECURRING = "RECURRING"
        const val TITLE = "TITLE"
        const val VIBRATION = "VIBRATION"

    }

}
