package com.msmmhm.hauimetyourmother

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.msmmhm.hauimetyourmother.databinding.FragmentAddEventBinding
import java.util.*


class AddEventFragment : Fragment() {
    lateinit var binding: FragmentAddEventBinding
    var date = ""
    var time = ""
    var park = ""

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
        binding.NewEventButton.setOnClickListener{
            addEvent()
        }



        val spinner: Spinner = binding.citySelector
        var sharedPref : SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val city = sharedPref.getString("selectedCity","")!!
        spinner.setSelection(getIndex(spinner, city))
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCity = parent?.getItemAtPosition(position) as String
                var sharedPref : SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
                var editor = sharedPref.edit()
                editor.putString("selectedCity",selectedCity)
                editor.commit()



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        return binding.root
    }

    private fun getIndex(spinner: Spinner, city: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == city) {
                return i
            }
        }
        return 0
    }


    private fun addEvent() {
        //ADD VERIFY DATA
        val date = binding.DateSelector.text.toString()
        val time = binding.TimeSelector.text.toString()
        //park = "test_park" //TO BE CHANGED
        var sharedPref : SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val park = sharedPref.getString("selectedPark","")!!

        val newEvent: Event = Event(date, time, park)
        newEvent.saveToDatabase()
        findNavController().popBackStack()

    }

    private fun timeClicked() {
        val timePicker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(18)
                .setMinute(30)
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