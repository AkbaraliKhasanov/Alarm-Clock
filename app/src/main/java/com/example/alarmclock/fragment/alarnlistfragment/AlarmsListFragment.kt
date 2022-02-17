package com.example.alarmclock.fragment.alarnlistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmclock.*
import com.example.alarmclock.adapter.AlarmRecyclerViewAdapter
import com.example.alarmclock.databinding.FragmentListalarmsBinding
import com.example.alarmclock.utils.Alarm
import com.example.alarmclock.utils.OnToggleAlarmListener
import com.example.alarmclock.viewmodel.AlarmsListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AlarmsListFragment : Fragment(), OnToggleAlarmListener {
    private var alarmRecyclerViewAdapter: AlarmRecyclerViewAdapter? = null
    private var alarmsListViewModel: AlarmsListViewModel? = null
    private var alarmsRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmRecyclerViewAdapter = AlarmRecyclerViewAdapter(this)
        alarmsListViewModel = ViewModelProviders.of(this).get(AlarmsListViewModel::class.java)
        alarmsListViewModel?.alarmsLiveData?.observe(
            this
        ) { alarms ->
            if (alarms != null) {
                alarmRecyclerViewAdapter!!.setAlarms(alarms)
            }
        }
    }

    lateinit var binding: FragmentListalarmsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListalarmsBinding.inflate(layoutInflater)

        binding.fragmentListalarmsRecylerView.adapter = alarmRecyclerViewAdapter
        alarmsRecyclerView?.adapter = alarmRecyclerViewAdapter

        binding.fragmentListalarmsAddAlarm.setOnClickListener(View.OnClickListener { v ->
            findNavController(v).navigate(R.id.addAlarmFragment)
        })

        return binding.root

    }

    override fun onToggle(alarm: Alarm?) {
        if (alarm!!.isStarted) {
            alarm.cancelAlarm(requireContext())
            alarmsListViewModel?.update(alarm)
        } else {
            alarm.schedule(requireContext())
            alarmsListViewModel?.update(alarm)
        }
    }

    override fun onDelete(alarm: Alarm?) {
        alarmsListViewModel?.delete(alarm!!)
    }
}