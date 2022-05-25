package com.msmmhm.hauimetyourmother

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.msmmhm.hauimetyourmother.databinding.FragmentItemBinding
import com.msmmhm.hauimetyourmother.placeholder.Friend.FriendItem


class MyFriendRecyclerViewAdapter(
    private val values: List<FriendItem>,
    val context: Context
) : RecyclerView.Adapter<MyFriendRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        var events: CollectionReference = FirebaseFirestore.getInstance().collection("events")
        val docRef = events.document(item.id)
        Log.i(ContentValues.TAG,"EVENTS ITEM ID: "+item.id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists())
                {
                    Log.i(ContentValues.TAG,"DOCUMENT EXISTS")
                    val map = document.data
                    if (map!!.size != 0)
                    {
                        holder.activeIcon.setImageResource(R.drawable.presence_online)
                    }
                    else
                    {
                        holder.activeIcon.setImageResource(R.drawable.presence_invisible)
                    }

                }
                else
                {
                    holder.activeIcon.setImageResource(R.drawable.presence_invisible)
                }
            }


        Log.i(ContentValues.TAG,"ITEM ID: ${item.id}.jpg")


                //Something not right



        /*holder.friendImg.isDrawingCacheEnabled = true
        holder.friendImg.buildDrawingCache()
        val bitmap = (holder.friendImg.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos)
        val data = baos.toByteArray()
        ImageRef.putBytes(data)*/



        //val resource = R.drawable.defaultprofilepicture
        //holder.friendImg.setImageResource(resource)
        holder.friendNickname.text = item.friendName


        loadWithGlide2(item.id,holder)

    }


    fun loadWithGlide2(id: String, holder: ViewHolder) {
        val storageReference = Firebase.storage.reference.child("ProfilePictures/${id}.jpg")
        Log.d(ContentValues.TAG, "NAME IS: ${storageReference.name}")

        Glide.with(context)
            .load(storageReference)
            .error(R.drawable.defaultprofilepicture)
            .into(holder.friendImg)
    }





    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val friendImg: ImageView = binding.friendPicture
        var friendNickname: TextView = binding.friendNickname
        var activeIcon: ImageView = binding.activeIcon
        val itemContainer: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + friendNickname.text + "'"
        }
    }

}