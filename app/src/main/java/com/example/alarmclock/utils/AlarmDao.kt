package com.example.alarmclock.utils

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.alarmclock.utils.Alarm

@Dao
interface AlarmDao {
    @Insert
    fun insert(alarm: Alarm)

    @Query("DELETE FROM alarm_table")
    fun deleteAll()

    @get:Query("SELECT * FROM alarm_table ORDER BY created ASC")
    val alarms: LiveData<List<Alarm>>

    @Update
    fun update(alarm: Alarm)

    @Delete
    fun deleteAlarm(alarm: Alarm)
}