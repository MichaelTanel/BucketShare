package com.qhackers.bucketshare

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_people.*

class PeopleFragment : ListFragment(), AdapterView.OnItemClickListener {

    var nameList: ArrayList<String> = ArrayList()
    private lateinit var listAdapter: ArrayAdapter<String>

    companion object {
        fun newInstance(): PeopleFragment {
            return PeopleFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.qhackers.bucketshare.R.layout.fragment_people, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nameList.clear()
        listAdapter = ArrayAdapter(context, com.qhackers.bucketshare.R.layout.list_item, nameList)
        peopleList.adapter = listAdapter
        peopleList.onItemClickListener = this@PeopleFragment
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        Toast.makeText(activity, "Item: $position", Toast.LENGTH_SHORT).show()
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    fun addItems() {
        listAdapter.notifyDataSetChanged()
    }
}
