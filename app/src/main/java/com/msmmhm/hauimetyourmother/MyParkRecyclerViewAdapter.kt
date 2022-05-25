package com.msmmhm.hauimetyourmother

import android.content.Context
import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.msmmhm.hauimetyourmother.placeholder.PlaceholderParksContent.PlaceholderItem
import com.msmmhm.hauimetyourmother.databinding.FragmentItemParkBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyParkRecyclerViewAdapter(
        private val values: List<Park>,
        private val eventListener: ParkListener
)
    : RecyclerView.Adapter<MyParkRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


    return ViewHolder(FragmentItemParkBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.name
        holder.contentView.text = "0"
        holder.container.setOnClickListener{
            eventListener.onItemClick(item.id)

        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemParkBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val container: ConstraintLayout = binding.container

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    private fun getListOfParks(data: Map<String, Map<String, String>>?): List<Park> {
        var list: List<Park> = emptyList()
        data?.forEach { (key, submap) ->
            list += Park(key, submap["name"]!!, submap["address"]!!)
        }
        return list
    }
}