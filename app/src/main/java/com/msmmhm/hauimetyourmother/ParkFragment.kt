package com.msmmhm.hauimetyourmother


import android.R.attr
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import android.widget.Toast

import android.R.attr.data
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController


/**
 * A fragment representing a list of Items.
 */
class ParkFragment : Fragment(), ParkListener {
    var auth: FirebaseAuth = Firebase.auth
    var parks: CollectionReference = FirebaseFirestore.getInstance().collection("dogParks")
    var selectedParkId: String = ""
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list_park, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                var sharedPref: SharedPreferences =
                    requireActivity().getPreferences(Context.MODE_PRIVATE)
                val park = sharedPref.getString("selectedCity", "Poznan")!!

                val docRef = parks.document(park)
                val addOnFailureListener = docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val data = document.data as Map<String, Map<String, String>>
                            adapter =
                                MyParkRecyclerViewAdapter(getListOfParks(data), this@ParkFragment)
                        } else {
                            Log.d(ContentValues.TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                    }
                addOnFailureListener


            }
        }
        var sharedPref: SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val mOnSharedPreferenceChangeListener =
            OnSharedPreferenceChangeListener { sharedPreferences, key ->
                lifecycleScope.launchWhenResumed {
                    findNavController().navigate(R.id.action_parkFragment_self)
                }
            }
        sharedPref.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener)
        return view
    }

    private fun getListOfParks(data: Map<String, Map<String, String>>?): List<Park> {
        var list: List<Park> = emptyList()
        data?.forEach { (key, submap) ->
                list += Park(key, submap["name"]!!, submap["address"]!!)
            }
        return list
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ParkFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

    override fun onItemClick(id: String) {
        selectedParkId = id
        var sharedPref : SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        var editor = sharedPref.edit()
        editor.putString("selectedPark",selectedParkId)
        editor.commit()
    }


}