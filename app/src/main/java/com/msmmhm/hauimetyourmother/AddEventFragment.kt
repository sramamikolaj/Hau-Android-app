package com.msmmhm.hauimetyourmother


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.msmmhm.hauimetyourmother.databinding.FragmentAddEventBinding
import java.util.*


class AddEventFragment : Fragment() {
    lateinit var binding: FragmentAddEventBinding
    var date = ""
    var time = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(layoutInflater)


        binding.DateSelector.setOnClickListener{
            dateClicked()
        }
        binding.TimeSelector.setOnClickListener{
            timeClicked()
        }


        return binding.root
    }

    private fun timeClicked() {
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(18)
                .setMinute(0)
                .build()
        timePicker.show(parentFragmentManager, timePicker.toString())

        timePicker.addOnPositiveButtonClickListener {
            val Hour: Int = timePicker.hour
            val Minute: Int = timePicker.minute
            time = "${Hour}:${Minute}"
            binding.TimeSelector.text = time
        }

    }

    private fun dateClicked() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("When?")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.show(parentFragmentManager, datePicker.toString())

        datePicker.addOnPositiveButtonClickListener { selection ->
            val utc: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            utc.timeInMillis = selection
            date = "${utc.get(Calendar.DAY_OF_MONTH)}.${utc.get(Calendar.MONTH)}.${utc.get(Calendar.YEAR)}"
            binding.DateSelector.text = date
        }
    }

}