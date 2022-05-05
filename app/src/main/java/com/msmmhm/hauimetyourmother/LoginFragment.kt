package com.msmmhm.hauimetyourmother

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msmmhm.hauimetyourmother.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        if(auth.currentUser != null){
            userLoggedIn()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.GoToRegistrationButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.LoginButton.setOnClickListener{
            userLogin()
        }
    }

    private fun userLogin() {
        val email = binding.LoginEmailInput.text.toString()
        val password = binding.LoginPasswordInput.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.w(ContentValues.TAG, "loginWithEmail:success", task.exception)
                    Toast.makeText(getContext(), "Login.", Toast.LENGTH_LONG)
                        .show()
                    userLoggedIn()
                } else {
                    Log.w(ContentValues.TAG, "loginWithEmail:failure", task.exception)
                    Toast.makeText(getContext(), "Invalid email or password.", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    private fun userLoggedIn(){
        val i = Intent(this.context, MainActivity::class.java)
        activity?.finish()
        startActivity(i)
    }
}