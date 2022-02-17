package com.example.alarmclock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.alarmlist.AlarmViewHolder
import com.example.alarmclock.utils.OnToggleAlarmListener
import com.example.alarmclock.R
import com.example.alarmclock.databinding.ItemAlarmBinding
import com.example.alarmclock.utils.Alarm

class AlarmRecyclerViewAdapter(listener: OnToggleAlarmListener) :
    RecyclerView.Adapter<AlarmViewHolder>() {
    private var alarms: List<Alarm>
    private val listener: OnToggleAlarmListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(ItemAlarmBinding.bind(itemView), listener)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.bind(alarm)

    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: AlarmViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.itemAlarmStarted.setOnCheckedChangeListener(null)
    }

    init {
        alarms = ArrayList()
        this.listener = listener

    }
}

