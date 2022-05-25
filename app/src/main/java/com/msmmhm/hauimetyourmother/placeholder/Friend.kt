package com.msmmhm.hauimetyourmother.placeholder

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object Friend {

    private var auth: FirebaseAuth = Firebase.auth
    private var userProfiles: CollectionReference = FirebaseFirestore.getInstance().collection("userProfiles")
    private var friendsLists: CollectionReference = FirebaseFirestore.getInstance().collection("friendsLists")

    val ITEMS: MutableList<FriendItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, FriendItem> = HashMap()

    private var name = ""
    private var email = ""

    init {
        //Document friendsLists of current user

        val docRef = friendsLists.document(auth.currentUser?.uid.toString())


        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    //Data and list friendList of current user
                    val data = document.data
                    val list = (data?.get("friendList")) as ArrayList<String>

                    var friend : FriendItem
                    Log.d(ContentValues.TAG, "FRIEND::CLASS:List = "+"${list!!::class.simpleName}")
                    //println("${list::class.simpleName}")



                    //Iterate
                    for (value in list)
                    {
                        Log.d(ContentValues.TAG,"FRIEND::CLASS:"+value.toString())
                        Log.d(ContentValues.TAG,value.toString())


                        //Document userProfiles of a friend
                        val docRef2 = userProfiles.document(value.toString())
                        docRef2.get()
                            .addOnSuccessListener { document2 ->
                                if (document2 != null)
                                {
                                    val data2 = document2.data
                                    name = data2?.get("username").toString()
                                    email = data2?.get("email").toString()
                                    Log.d(ContentValues.TAG, "FRIEND::CLASS:FriendId = " + value.toString())
                                    Log.d(ContentValues.TAG, "FRIEND::CLASS:FriendName = " + name)
                                    Log.d(ContentValues.TAG, "FRIEND::CLASS:FriendEmail = " + email)

                                    friend = FriendItem(value,name,email)
                                    friend.print()
                                    addItem(friend)

                                }
                        }


                    }

                } else {
                    Log.d(ContentValues.TAG, "FRIEND::CLASS:No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "FRIEND::CLASS:get failed with ", exception)
            }


    }

    private fun addItem(item: FriendItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createPlaceholderItem(position: Int): FriendItem {
        return FriendItem(position.toString(), "Item " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class FriendItem(val id: String, var friendName: String, var email: String, val active: ACTIVE=ACTIVE.INACTIVE) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                ACTIVE.values()[parcel.readInt()]) {
        }

        override fun toString(): String = friendName

        fun print()
        {
            Log.d(ContentValues.TAG,"FRIEND_ITEM::PRINT, id="+id+" name="+friendName+" email="+email)
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(friendName)
            parcel.writeString(email)
            parcel.writeInt(active.ordinal)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<FriendItem> {
            override fun createFromParcel(parcel: Parcel): FriendItem {
                return FriendItem(parcel)
            }

            override fun newArray(size: Int): Array<FriendItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}

enum class ACTIVE
{
    ACTIVE,INACTIVE
}


