package com.example.alarmclock.utils

import com.example.alarmclock.utils.Alarm

interface OnToggleAlarmListener {
    fun onToggle(alarm: Alarm?)
    fun onDelete(alarm: Alarm?)
}