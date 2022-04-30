package com.msmmhm.hauimetyourmother

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msmmhm.hauimetyourmother.databinding.ActivityMainBinding

//Test

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        //Temp///////////////
        val name = auth.currentUser?.email
        binding.TempText.text = "Hello there "+name
        binding.TempLogOut.setOnClickListener{
            auth.signOut()
            val i = Intent(this, LoginAndRegister::class.java)
            finish()
            startActivity(i)
        }
        ///////////////////////
    }


}