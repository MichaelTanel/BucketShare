package com.qhackers.bucketshare

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ArrayAdapter



class MyListFragment : ListFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resources, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = ArrayAdapter.createFromResource(
            activity!!,
            R.array.Resources, android.R.layout.simple_list_item_1
        )
        listAdapter = adapter
        listView.setOnItemClickListener{ _, _, position, _ ->
            val resArray = resources.getStringArray(R.array.Resources)
            Toast.makeText(activity, "Item: ${resArray[position]}", Toast.LENGTH_SHORT).show()
        }
    }
}
