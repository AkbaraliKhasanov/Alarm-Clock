package com.example.alarmclock.alarmlist

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.R
import com.example.alarmclock.databinding.ItemAlarmBinding
import com.example.alarmclock.utils.Alarm
import com.example.alarmclock.utils.OnToggleAlarmListener
import java.lang.String

class AlarmViewHolder(var binding: ItemAlarmBinding, listener: OnToggleAlarmListener) :
    RecyclerView.ViewHolder(binding.root) {
    private val listener: OnToggleAlarmListener = listener

    @SuppressLint("DefaultLocale")
    fun bind(alarm: Alarm) {
        setListeners(alarm)
        val alarmText = String.format("%02d:%02d", alarm.hour, alarm.minute)
        binding.itemAlarmTime.text = alarmText
        binding.itemAlarmStarted.isChecked = alarm.isStarted
        binding.itemAlarmTitle.text = alarm.title

        if (alarm.isStarted) {
            binding.itemAlarmTime.setTextColor(Color.parseColor("#000000"))
        } else {
            binding.itemAlarmTime.setTextColor(Color.parseColor("#BEBEBE"))
        }
        if (!alarm.isRecurring) {
            binding.itemAlarmRecurringDays.text = "Bir marta"
        } else {
            binding.itemAlarmRecurringDays.text = alarm.recurringDaysText
        }


    }

    private fun setListeners(alarm: Alarm) {
        binding.root.setOnLongClickListener {
            listener.onDelete(alarm)
            true
        }
        binding.itemAlarmStarted.setOnCheckedChangeListener { buttonView, isChecked ->
            listener.onToggle(alarm)
            if (isChecked) {
                binding.itemAlarmTime.setTextColor(Color.parseColor("#000000"))
            } else {
                binding.itemAlarmTime.setTextColor(Color.parseColor("#BEBEBE"))

            }

        }

    }


}
