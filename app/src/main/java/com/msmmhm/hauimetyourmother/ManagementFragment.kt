package com.msmmhm.hauimetyourmother

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msmmhm.hauimetyourmother.databinding.ActivityMainBinding
import com.msmmhm.hauimetyourmother.databinding.FragmentLoginBinding
import com.msmmhm.hauimetyourmother.databinding.FragmentManagementBinding


class ManagementFragment : Fragment() {
    private lateinit var binding: FragmentManagementBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagementBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Temporary username shower
        binding.UserEmail.text = auth.currentUser?.email

        //Log out button
        binding.SignOutButton.setOnClickListener{
            auth.signOut()
            val i = Intent(activity, LoginAndRegister::class.java)
            activity?.finish()
            startActivity(i)
        }
    }

}