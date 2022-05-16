package com.msmmhm.hauimetyourmother

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

import com.msmmhm.hauimetyourmother.placeholder.Friend.FriendItem
import com.msmmhm.hauimetyourmother.databinding.FragmentItemBinding
import java.io.ByteArrayOutputStream

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyFriendRecyclerViewAdapter(
    private val values: List<FriendItem>
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
        val storage = Firebase.storage
        val storageRef = storage.reference
        val ImageRef = storageRef.child("ProfilePictures/${item.id}.jpg")
        Log.i(ContentValues.TAG,"ITEM ID: ${item.id}.jpg")


                //Something not right

        /*
        holder.friendImg.isDrawingCacheEnabled = true
        holder.friendImg.buildDrawingCache()
        val bitmap = (holder.friendImg.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos)
        val data = baos.toByteArray()
        ImageRef.putBytes(data)
        */

        val resource = R.drawable.defaultprofilepicture
        holder.friendImg.setImageResource(resource)
        holder.friendNickname.text = item.friendName
        holder.friendEmail.text = item.email
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val friendImg: ImageView = binding.friendPicture
        val friendNickname: TextView = binding.friendNickname
        val friendEmail: TextView = binding.friendEmail

        override fun toString(): String {
            return super.toString() + " '" + friendNickname.text + "'"
        }
    }

}