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

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        Log.w(ContentValues.TAG,"USER="+auth.currentUser.toString())
        if(auth.currentUser != null)
        {
            userLoggedIn() // <- to be deleted when email verification is set up
            /*  UNCOMMENT TO START EMAIL VERIFICATION
            if(auth.currentUser!!.isEmailVerified())
            {
                Log.w(ContentValues.TAG,"userLoggedIn")
                userLoggedIn()
            } else {
                Toast.makeText(getContext(), "Please verify your e-mail", Toast.LENGTH_SHORT).show()
            }
            */
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
                    userLoggedIn()     //DELETE TO SET UP MAIL VERIFICATION + UNCOMMENT BELOW
                    /*
                    if (auth.currentUser!!.isEmailVerified())
                    {
                        Toast.makeText(getContext(), "Logged in", Toast.LENGTH_SHORT).show()
                        Log.w(ContentValues.TAG, "loginWithEmail:success"+auth.currentUser.toString(), task.exception)
                        userLoggedIn()
                    } else
                    {
                        Toast.makeText(getContext(), "Please verify e-mail", Toast.LENGTH_SHORT).show();
                        Log.w(ContentValues.TAG,"loginWithEmail:failure:Email not verified")
                    }
                    */
                } else {
                    Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_LONG).show()
                    Log.w(ContentValues.TAG, "loginWithEmail:failure:Wrong email or password", task.exception)
                }
            }
    }

    private fun userLoggedIn(){
        val i = Intent(this.context, MainActivity::class.java)
        activity?.finish()
        startActivity(i)
    }
}