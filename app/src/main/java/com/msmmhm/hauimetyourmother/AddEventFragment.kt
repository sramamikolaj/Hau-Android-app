package com.msmmhm.hauimetyourmother

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.msmmhm.hauimetyourmother.databinding.FragmentAddEventBinding


class AddEventFragment : Fragment() {
    lateinit var binding: FragmentAddEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(layoutInflater)


        return binding.root
    }

}