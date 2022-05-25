package com.msmmhm.hauimetyourmother

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.msmmhm.hauimetyourmother.databinding.FragmentItemListBinding
import com.msmmhm.hauimetyourmother.placeholder.Friend


/**
 * A fragment representing a list of Items.
 */
class FriendFragment : Fragment() {

    private lateinit var binding: FragmentItemListBinding
    private lateinit var  mSwipeRefreshLayout: SwipeRefreshLayout




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemListBinding.inflate(inflater, container, false)

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyFriendRecyclerViewAdapter(Friend.ITEMS,container!!.context)

            return binding.root

        }

    }

    override fun onResume() {
        super.onResume()
    }


}