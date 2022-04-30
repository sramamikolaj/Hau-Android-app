package com.msmmhm.hauimetyourmother

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//Test

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        if(!checkCachedData()){
            val intent: Intent = Intent(this, LoginAndRegister::class.java)
            startActivity(intent)
        }
        
    }
    //Check if cached login data exists
    private fun checkCachedData(): Boolean {
        val prefs: SharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val email: String? = prefs.getString("email", "")
        val password: String? = prefs.getString("password", "")
        if(email.equals("") or password.equals("")) return false
        return true
    }
}