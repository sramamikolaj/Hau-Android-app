package com.msmmhm.hauimetyourmother

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback


class AddFriendQrScanFragment : Fragment() {


    lateinit var binding: AddFriendQrScanFragment
    private lateinit var codeScanner: CodeScanner
    var scannedText = ""
    //when scanned add to firebase string that contains userID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = AddFriendQrScanFragment.inflate(layoutInflater)

        //return binding.root
        return inflater.inflate(R.layout.fragment_add_friend_qr_scan, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        //val refreshToast = view.findViewById<CodeScannerView>(R.id.refreshToast)

        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_SHORT).show()
                scannedText = it.text
            }
        }
//        refreshToast.setOnClickListener{
//            Toast.makeText(activity,scannedText,Toast.LENGTH_SHORT).show()
//        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

        override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }




}


