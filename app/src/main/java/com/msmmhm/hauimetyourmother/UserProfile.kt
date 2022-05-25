package com.msmmhm.hauimetyourmother

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserProfile(

){
    private var auth: FirebaseAuth = Firebase.auth
    private var userProfiles: CollectionReference = FirebaseFirestore.getInstance().collection("userProfiles")

    //var test = userProfiles.
    //User's profile variables:
    private var username = ""
    private var email = ""


    fun setData(usernameToSet: String, emailToSet: String){
        username = usernameToSet
        email = emailToSet
    }
    fun getHashMap(): HashMap<String, String> {
        return hashMapOf(
            "username" to username,
            "email" to email
        )
    }
    fun setUsername(toSet: String){
        username = toSet
        updateToDatabase()
    }
    fun updateToDatabase(){
        userProfiles.document(auth.currentUser?.uid.toString()).set(getHashMap())
    }
    fun getFromDatabase(){
        val docRef = userProfiles.document(auth.currentUser?.uid.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    username = data?.get("username").toString()
                    email = data?.get("email").toString()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

}