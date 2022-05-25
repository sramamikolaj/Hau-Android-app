package com.msmmhm.hauimetyourmother

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.msmmhm.hauimetyourmother.databinding.FragmentManagementBinding
import java.io.ByteArrayOutputStream


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
        updateTextViews()
        loadWithGlide()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Log out button
        binding.SignOutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
            val i = Intent(activity, LoginAndRegister::class.java)
            activity?.finish()
            startActivity(i)
        }
        binding.profilePicture.setOnClickListener {
            openGallery()
        }
        binding.EditUsernameButton.setOnClickListener{
            changeNickname()
        }
        binding.SaveNewUSernameButton.setOnClickListener{
            nicknameChanged()
        }

    }

    private fun nicknameChanged() {
        val newUsername = binding.EditNicknameField.text.toString()
        binding.EditNicknameField.text.clear()
        binding.Username.text = newUsername
        (activity as MainActivity).userProfile.setUsername(newUsername)


        binding.Username.visibility = VISIBLE
        binding.EditUsernameButton.visibility = VISIBLE
        binding.EditNicknameField.visibility = INVISIBLE
        binding.SaveNewUSernameButton.visibility = INVISIBLE
    }


    private fun updateTextViews() {
        binding.Username.text = (activity as MainActivity).userProfile.getHashMap()["username"]
        binding.EmailAddress.text = (activity as MainActivity).userProfile.getHashMap()["email"]
    }
    private fun changeNickname(){
        binding.Username.visibility = INVISIBLE
        binding.EditUsernameButton.visibility = INVISIBLE
        binding.EditNicknameField.visibility = VISIBLE
        binding.SaveNewUSernameButton.visibility = VISIBLE
        binding.EditNicknameField.setText((activity as MainActivity).userProfile.getHashMap()["username"])

    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                var uri: Uri? = result.data?.data
                binding.profilePicture.setImageURI(uri)
                profilePictureChange(uri)
            }
        }

    private fun profilePictureChange(uri: Uri?) {
        /*val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainImagesRef = storageRef.child("ProfilePictures/${auth.currentUser?.uid.toString()}.jpg")
        binding.profilePicture.isDrawingCacheEnabled = true
        binding.profilePicture.buildDrawingCache()
        val bitmap = (binding.profilePicture.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos)
        val data = baos.toByteArray()
        mountainImagesRef.putBytes(data)*/

        loadWithGlide()
    }
    private fun loadWithGlide() {
        val storageReference = Firebase.storage.reference.child("ProfilePictures/${auth.currentUser?.uid.toString()}.jpg")
        Log.d(TAG, "NAME IS: ${storageReference.name}")
        Glide.with(this /* context */)
            .load(storageReference)
            .error(R.drawable.defaultprofilepicture)
            .into(binding.profilePicture)
    }



}