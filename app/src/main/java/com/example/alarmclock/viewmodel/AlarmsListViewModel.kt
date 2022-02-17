package com.example.alarmclock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.alarmclock.repository.AlarmRepository
import com.example.alarmclock.utils.Alarm

class AlarmsListViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: AlarmRepository = AlarmRepository(application)
    val alarmsLiveData: LiveData<List<Alarm>> = alarmRepository.getAlarmsLiveData()

    fun update(alarm: Alarm) {
        alarmRepository.update(alarm)
    }

    fun delete(alarm: Alarm) {
        alarmRepository.delete(alarm)
    }

}