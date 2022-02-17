package com.example.alarmclock.service

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import com.example.alarmclock.repository.AlarmRepository

class RescheduleAlarmsService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val alarmRepository = AlarmRepository(application)
        alarmRepository.getAlarmsLiveData().observe(
            this
        ) { alarms ->
            for (a in alarms) {
                if (a.isStarted) {
                    a.schedule(applicationContext)
                }
            }
        }
        return START_STICKY
    }


    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}
