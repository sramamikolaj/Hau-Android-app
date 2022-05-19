package com.msmmhm.hauimetyourmother

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.msmmhm.hauimetyourmother.databinding.FragmentAddFriendBinding


class AddFriendFragment : Fragment() {
    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var  qrCodeView: ImageView
    private lateinit var enterData : EditText
    private lateinit var  generateQrButton: Button
    private lateinit var  scanQrCode: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFriendBinding.inflate(layoutInflater)

        qrCodeView = binding.qrCodeImg
        enterData = binding.enterText
        generateQrButton = binding.generateQrButton
        scanQrCode = binding.scanQrButton


        generateQrButton.setOnClickListener{
            val data = enterData.text.toString().trim()

            if(data.isEmpty()) {
                Toast.makeText(context,"enter some data",Toast.LENGTH_SHORT).show()
            }else {

                val writer = QRCodeWriter()
                try{
                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512,512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
                    for(x in 0 until width) {
                        for (y in 0 until height) {
                            bmp.setPixel(x,y,if (bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    qrCodeView.setImageBitmap(bmp)



                }catch (e: WriterException) {
                    e.printStackTrace()
                }
            }
        }

        scanQrCode.setOnClickListener {
            findNavController().navigate(R.id.action_addFriendFragment_to_addFriendQrScanFragment)
        }


        return binding.root
    }

}