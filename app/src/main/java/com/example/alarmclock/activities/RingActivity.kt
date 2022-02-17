package com.example.alarmclock.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmclock.utils.Alarm
import com.example.alarmclock.service.AlarmService
import com.example.alarmclock.databinding.ActivityRingBinding
import java.text.SimpleDateFormat
import java.util.*

class RingActivity : AppCompatActivity() {

    lateinit var binding: ActivityRingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setValueAlarm()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setValueAlarm() {
        val hour = SimpleDateFormat("HH").format(Date())
        val minute = SimpleDateFormat("mm").format(Date())
        binding.date.text = "$hour:$minute"
    }

    private fun setListeners() {
        binding.activityRingDismiss.setOnClickListener {
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
        binding.activityRingSnooze.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.MINUTE, 10)
            val alarm = Alarm(
                Random().nextInt(Int.MAX_VALUE),
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                "10 minutga qoldirish",
                System.currentTimeMillis(),
                started = true,
                recurring = false,
                monday = false,
                tuesday = false,
                wednesday = false,
                thursday = false,
                friday = false,
                saturday = false,
                sunday = false,
                isVibration = false
            )
            alarm.schedule(applicationContext)
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }
    }
}
