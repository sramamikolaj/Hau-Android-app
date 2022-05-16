package com.msmmhm.hauimetyourmother

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.msmmhm.hauimetyourmother.databinding.FragmentItemListBinding
import com.msmmhm.hauimetyourmother.placeholder.Friend

/**
 * A fragment representing a list of Items.
 */
class FriendFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemListBinding.inflate(inflater, container, false)

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyFriendRecyclerViewAdapter(Friend.ITEMS)

            return binding.root

        }
        /*val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyFriendRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }
    */


        //private var columnCount = 1
    }



}