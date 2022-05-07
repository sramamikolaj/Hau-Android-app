package com.msmmhm.hauimetyourmother

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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

        //Log out button
        binding.SignOutButton.setOnClickListener{
            auth.signOut()
            Toast.makeText(context,"Signed out",Toast.LENGTH_SHORT).show()
            val i = Intent(activity, LoginAndRegister::class.java)
            activity?.finish()
            startActivity(i)
        }
        binding.FirestoreTestButton.setOnClickListener {
            val test = (activity as MainActivity).userProfile.getHashMap().get("username")
            Toast.makeText(context, test, Toast.LENGTH_SHORT).show()
        }
    }

}