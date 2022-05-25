package com.msmmhm.hauimetyourmother

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Event {
    private var auth: FirebaseAuth = Firebase.auth
    private var events: CollectionReference = FirebaseFirestore.getInstance().collection("events")

    private var date: String = "00.00.00"
    private var time: String = "00:00"
    private var park: String = ""

    constructor()
    constructor(dateToSet: String, timeToSet: String, parkToSet: String){
        date = dateToSet;
        time = timeToSet;
        park = parkToSet
    }


    fun saveToDatabase(){
        val map = mapOf("date" to date, "park" to park, "time" to time )
        val eventName = date.replace('.', '-')+'_'+time
        var docRef = events.document(auth.currentUser?.uid.toString())

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if(document != null) {
                    if (document.exists()) {
                        events.document(auth.currentUser?.uid.toString()).update(eventName, map)
                    } else {
                        events.document(auth.currentUser?.uid.toString()).set(hashMapOf(eventName to map))
                    }
                }
            } else {
                Log.d("TAG", "Error: ", task.exception)
            }
        }
    }


}