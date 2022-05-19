package com.msmmhm.hauimetyourmother

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.msmmhm.hauimetyourmother.databinding.FragmentDashboardBinding

class AddFriendQrScanFragment : Fragment() {
    lateinit var binding: AddFriendQrScanFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = AddFriendQrScanFragment.inflate(layoutInflater)


       //return binding.root
        return inflater.inflate(R.layout.fragment_add_friend_qr_scan, container, false)
    }


}