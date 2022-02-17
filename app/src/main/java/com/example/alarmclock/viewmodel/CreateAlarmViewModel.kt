package com.example.alarmclock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alarmclock.repository.AlarmRepository
import com.example.alarmclock.utils.Alarm

class CreateAlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: AlarmRepository = AlarmRepository(application)
    fun insert(alarm: Alarm) {
        alarmRepository.insert(alarm)
    }

}
