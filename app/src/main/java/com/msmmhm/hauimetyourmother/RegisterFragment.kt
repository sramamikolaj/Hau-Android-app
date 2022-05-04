package com.msmmhm.hauimetyourmother

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msmmhm.hauimetyourmother.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }
    //TEST 2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.GoToLoginButton.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.RegisterButton.setOnClickListener{
            accountRegister()
        }
    }

    private fun accountRegister() {
        val email = binding.EmailInput.text.toString()
        val password1 = binding.PasswordFirstInput.text.toString()
        val password2 = binding.passwordSecondInput.text.toString()
        if(checkData(email, password1, password2)) {
            auth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(requireView(), "Account created successfully", Snackbar.LENGTH_LONG)
                            .show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Snackbar.make(requireView(), "Failed to register new account", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }

    private fun checkData(email: String, password1: String, password2: String): Boolean {
        val emailRegex = Regex("^\\S+@\\S+\\.\\S+$")
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
        if(!email.matches(emailRegex)) {
            Snackbar.make(requireView(), "Wrong email address", Snackbar.LENGTH_LONG)
                .show()
            return false
        } else if(password1 != password2){
            Snackbar.make(requireView(), "Passwords aren't identical", Snackbar.LENGTH_LONG)
                .show()
            return false
        } else if(!password1.matches(passwordRegex)){
            Snackbar.make(requireView(), "Password must contain both letters and numbers and be at least 8 letters long", Snackbar.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }
}