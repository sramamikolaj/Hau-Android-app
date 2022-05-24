package com.msmmhm.hauimetyourmother

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msmmhm.hauimetyourmother.databinding.ActivityMainBinding

//Test

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    var userProfile: UserProfile = UserProfile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfile.getFromDatabase()

        binding.GoToFriends.setOnClickListener{
            goToFriends()
        }
        binding.GoToManagement.setOnClickListener{
            goToManagement()
        }
        binding.GoToDashboard.setOnClickListener{
            goToDashboard()
        }
    }


    private fun goToFriends(){
        findNavController(R.id.MainNavigation).navigate(R.id.friendFragment) //Not working with binding

        //Later to put into function to make code more sexy
        binding.GoToDashboard.alpha = 0.5F
        binding.GoToManagement.alpha = 0.5F
        binding.GoToFriends.alpha = 1F
        //Later animated
    }

    private fun goToManagement(){
        findNavController(R.id.MainNavigation).navigate(R.id.managementFragment) //Not working with binding

        //Later to put into function to make code more sexy
        binding.GoToDashboard.alpha = 0.5F
        binding.GoToManagement.alpha = 1F
        binding.GoToFriends.alpha = 0.5F
        //Later animated
    }

    private fun goToDashboard(){
        findNavController(R.id.MainNavigation).navigate(R.id.dashboardFragment) //Not working with binding
        //Later to put into function to make code more sexy
        binding.GoToDashboard.alpha = 1F
        binding.GoToManagement.alpha = 0.5F
        binding.GoToFriends.alpha = 0.5F
    }

}