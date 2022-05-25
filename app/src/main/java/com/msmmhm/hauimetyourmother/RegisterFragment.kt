package com.msmmhm.hauimetyourmother

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
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
                        //Toast.makeText(getContext(), "Registered", Toast.LENGTH_LONG).show()
                        Log.w(TAG, "createUserWithEmail:success", task.exception)

                        val createdUser: UserProfile = UserProfile()
                        createdUser.setData(email.substring(0, email.indexOf('@')), email)
                        createdUser.updateToDatabase()


                        /////////Email verification -> uncomment later/////////
                        //val user = Firebase.auth.currentUser
                        /* user!!.sendEmailVerification()
                            .addOnSuccessListener {
                                //Toast.makeText(getContext(), "Instructions Sent...", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(getContext(), "Failed to send due to " + e.message, Toast.LENGTH_SHORT).show()
                            } */
                        //////////////////////////////////////////////////////

                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_LONG).show()
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                    }
                }
        }
    }

    private fun checkData(email: String, password1: String, password2: String): Boolean {
        val emailRegex = Regex("^\\S+@\\S+\\.\\S+$")
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
        if(!email.matches(emailRegex)) {
            Toast.makeText(getContext(), "Invalid email.",
                Toast.LENGTH_LONG).show()
            return false
        } else if(password1 != password2){
            Toast.makeText(getContext(), "Unidentical passwords",
                Toast.LENGTH_LONG).show()
            return false
        } else if(!password1.matches(passwordRegex)){
            Snackbar.make(requireView(), "Password must contain both letters and numbers and be at least 8 letters long", Snackbar.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }
}