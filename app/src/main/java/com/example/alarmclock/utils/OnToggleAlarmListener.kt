package com.example.alarmclock.utils


interface OnToggleAlarmListener {
    fun onToggle(alarm: Alarm?)
    fun onDelete(alarm: Alarm?)
}