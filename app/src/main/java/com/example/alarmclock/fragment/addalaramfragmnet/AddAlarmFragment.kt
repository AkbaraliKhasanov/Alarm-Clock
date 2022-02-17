package com.example.alarmclock.fragment.addalaramfragmnet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import butterknife.ButterKnife
import com.example.alarmclock.utils.Alarm
import com.example.alarmclock.utils.AlarmDatabase
import com.example.alarmclock.viewmodel.CreateAlarmViewModel
import com.example.alarmclock.R
import com.example.alarmclock.databinding.BottomSheetDialogBinding
import com.example.alarmclock.databinding.DescBottomSheetBinding
import com.example.alarmclock.databinding.FragmentAddAlarmBinding
import com.example.alarmclock.databinding.RepeatBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


class AddAlarmFragment : Fragment() {
    var recurring = false
    var monday = false
    var tuesday = false
    var wednesday = false
    var thursday = false
    var friday = false
    var saturday = false
    var sunday = false
    var isRun = false
    var count = 0
    private var desc: String? = null
    var vibration = false
    private lateinit var binding: FragmentAddAlarmBinding
    private var createAlarmViewModel: CreateAlarmViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAlarmViewModel = ViewModelProviders.of(this)[CreateAlarmViewModel::class.java]

    }

    @SuppressLint("SoonBlockedPrivateApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddAlarmBinding.inflate(layoutInflater)
        hourPickerSetValue()
        minutePickerSetValue()
        setListeners()
        ButterKnife.bind(this, requireActivity())
        return binding.root
    }

    private fun setListeners() {
        binding.saveBtn.setOnClickListener {

            val alarmId = Random().nextInt(Int.MAX_VALUE)
            isRun = true
            // region ::Class
            val alarm = Alarm(
                alarmId,
                binding.numberPicker1.value,
                binding.numberPicker2.value,
                desc,
                System.currentTimeMillis(),
                started = true,
                recurring = recurring,
                monday = monday,
                tuesday = tuesday,
                wednesday = wednesday,
                thursday = thursday,
                friday = friday,
                saturday = saturday,
                sunday = sunday,
                isVibration = vibration
            )
            //endregion

            createAlarmViewModel?.insert(alarm)

            alarm.schedule(requireContext())

            findNavController().popBackStack()

        }



        binding.layout1.setOnClickListener {
            val bottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val bottomView = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null, false)
            bottomSheetDialog.setContentView(bottomView)
            bottomSheetDialog.show()
            val bind = BottomSheetDialogBinding.bind(bottomView)
            bind.layout1.setOnClickListener {
                count++
                recurring = false
                setRepetitionValue(bind.text1.text.toString())
                bottomSheetDialog.dismiss()

            }
            bind.layout2.setOnClickListener {
                recurring = true
                count++
                monday = true
                tuesday = true
                wednesday = true
                thursday = true
                friday = true
                sunday = true
                saturday = true
                setRepetitionValue(bind.text2.text.toString())
                bottomSheetDialog.dismiss()
            }
            bind.layout3.setOnClickListener {
                recurring = true
                count++
                monday = true
                tuesday = true
                wednesday = true
                thursday = true
                friday = true
                setRepetitionValue(bind.text3.text.toString())
                bottomSheetDialog.dismiss()
            }
            bind.layout4.setOnClickListener {

                setRepetitionValue(bind.text4.text.toString())
                setSelectionType()
                bottomSheetDialog.dismiss()
            }
        }


        binding.closeBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.decsBtn.setOnClickListener {
            val bottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val bottomView = layoutInflater.inflate(R.layout.desc_bottom_sheet, null, false)
            bottomSheetDialog.setContentView(bottomView)
            bottomSheetDialog.show()
            val descBottomSheetBinding = DescBottomSheetBinding.bind(bottomView)
            setDescription(descBottomSheetBinding, bottomSheetDialog)
        }
        //region::Check
        binding.vibration.setOnCheckedChangeListener { p0, isChecked ->
            if (isChecked) {
                vibration = true
            } else {
                vibration
            }
        }

        binding.layout2.setOnClickListener {
            binding.vibration.isChecked = vibration
            if (vibration) {
                binding.vibration.isChecked = false
                vibration = false
            } else {
                binding.vibration.isChecked = true
                vibration = true
            }
        }

    }


    private fun setDescription(
        descBottomSheetBinding: DescBottomSheetBinding,
        bottomSheetDialog: BottomSheetDialog
    ) {
        descBottomSheetBinding.desc.setText(desc)
        descBottomSheetBinding.cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        descBottomSheetBinding.positiveButton.setOnClickListener {
            binding.desc.text = descBottomSheetBinding.desc.text
            desc = descBottomSheetBinding.desc.text.toString()
            bottomSheetDialog.dismiss()
        }
    }

    private fun setSelectionType() {
        val bottomSheetDialog1 =
            BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val bottomView1 = layoutInflater.inflate(R.layout.repeat_bottom_sheet, null, false)
        bottomSheetDialog1.setContentView(bottomView1)
        bottomSheetDialog1.show()
        val bind1 = RepeatBottomSheetBinding.bind(bottomView1)
        if (count > 0) {
            monday = false
            tuesday = false
            wednesday = false
            thursday = false
            friday = false
            sunday = false
            saturday = false
        }
        count = 0
        setMonday(bind1)
        setTuesday(bind1)
        setWednesday(bind1)
        setThursday(bind1)
        setFriday(bind1)
        setSaturday(bind1)
        setSunday(bind1)
        bind1.cancelButton.setOnClickListener {
            bottomSheetDialog1.dismiss()
        }
        bind1.positiveButton.setOnClickListener {
            bottomSheetDialog1.dismiss()
        }
    }

    private fun setSunday(bind1: RepeatBottomSheetBinding) {
        var check = sunday
        bind1.sunday.isChecked = sunday
        bind1.sunday.setOnClickListener {
            if (check) {
                recurring = false
                bind1.sunday.isChecked = false
                check = false
                sunday = false
            } else {
                recurring = true
                sunday = true
                check = true
                bind1.sunday.isChecked = true
            }
        }
    }

    private fun setSaturday(bind1: RepeatBottomSheetBinding) {
        var check = saturday
        bind1.saturday.isChecked = saturday
        bind1.saturday.setOnClickListener {
            if (check) {
                recurring = false
                bind1.saturday.isChecked = false
                check = false
                saturday = false
            } else {
                recurring = true
                saturday = true
                check = true
                bind1.saturday.isChecked = true
            }
        }
    }

    private fun setFriday(bind1: RepeatBottomSheetBinding) {
        var check = friday
        bind1.friday.isChecked = friday
        bind1.friday.setOnClickListener {
            if (check) {
                recurring = false
                bind1.friday.isChecked = false
                check = false
                friday = false
            } else {
                recurring = true
                friday = true
                check = true
                bind1.friday.isChecked = true
            }
        }
    }

    private fun setThursday(bind1: RepeatBottomSheetBinding) {
        var check = thursday
        bind1.thursday.isChecked = thursday
        bind1.thursday.setOnClickListener {
            if (check) {
                recurring = false
                bind1.thursday.isChecked = false
                check = false
                thursday = false
            } else {
                recurring = true
                thursday = true
                check = true
                bind1.thursday.isChecked = true
            }
        }
    }

    private fun setWednesday(bind1: RepeatBottomSheetBinding) {
        var check = wednesday
        bind1.wednesday.isChecked = wednesday
        bind1.wednesday.setOnClickListener {
            if (check) {
                recurring = false
                bind1.wednesday.isChecked = false
                check = false
                wednesday = false
            } else {
                recurring = true
                wednesday = true
                check = true
                bind1.wednesday.isChecked = true
            }
        }
    }

    private fun setTuesday(bind1: RepeatBottomSheetBinding) {
        var check = tuesday
        bind1.tuesday.isChecked = tuesday
        bind1.tuesday.setOnClickListener {
            if (check) {
                recurring = false
                bind1.tuesday.isChecked = false
                check = false
                tuesday = false
            } else {
                recurring = true
                tuesday = true
                check = true
                bind1.tuesday.isChecked = true
            }
        }
    }

    private fun setMonday(bind1: RepeatBottomSheetBinding) {
        var check = monday
        bind1.monday.isChecked = monday
        bind1.monday.setOnClickListener {
            if (check) {
                recurring = false
                bind1.monday.isChecked = false
                check = false
                monday = false
            } else {
                recurring = true
                monday = true
                check = true
                bind1.monday.isChecked = true
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun minutePickerSetValue() {
        binding.numberPicker2.setOnLongPressUpdateInterval(100)
        binding.numberPicker2.wrapSelectorWheel = true
        binding.numberPicker2.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.numberPicker2.isSoundEffectsEnabled = true
        binding.numberPicker2.minValue = 0
        binding.numberPicker2.maxValue = 59
        binding.numberPicker2.setFormatter { p0 ->
            var value = ""
            value = if (p0 < 10) {
                "0$p0"
            } else {
                p0.toString()
            }
            value
        }
        val minute = SimpleDateFormat("mm").format(Date())
        binding.numberPicker2.value = minute.toInt()
    }

    @SuppressLint("SimpleDateFormat")
    private fun hourPickerSetValue() {
        binding.numberPicker1.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.numberPicker1.isSoundEffectsEnabled = true
        binding.numberPicker1.wrapSelectorWheel = true
        binding.numberPicker1.minValue = 0
        binding.numberPicker1.maxValue = 23
        binding.numberPicker1.setFormatter { p0 ->
            var value = ""
            value = if (p0 < 10) {
                "0$p0"
            } else {
                p0.toString()
            }
            value
        }
        val hour = SimpleDateFormat("HH").format(Date())
        binding.numberPicker1.value = hour.toInt()
    }

    private fun setRepetitionValue(value: String?) {
        binding.selectionType.text = value
    }


}
