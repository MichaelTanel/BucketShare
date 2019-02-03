package com.qhackers.bucketshare

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.AdapterView
import kotlinx.android.synthetic.main.fragment_my_list.*
import android.support.v7.widget.LinearLayoutManager
import android.R
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout


class MyListFragment : Fragment(), AdapterView.OnItemClickListener {

    var stringList: ArrayList<String> = ArrayList()
    private lateinit var listAdapter: MyListAdapter

    companion object {
        fun newInstance(): MyListFragment {
            return MyListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.qhackers.bucketshare.R.layout.fragment_my_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stringList.clear()
        listAdapter = MyListAdapter(stringList)
        list.adapter = listAdapter
        // Set layout manager to position the items
        list.layoutManager = LinearLayoutManager(context)
        list.layoutManager = GridLayoutManager(activity, 1)
//        list.onItemClickListener = this@MyListFragment

        fab.setOnClickListener {
            launchDialog()
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        Toast.makeText(activity, "Item: $position", Toast.LENGTH_SHORT).show()
    }

    fun launchDialog() {
        val builder = AlertDialog.Builder(this.context)
        val inflater = layoutInflater
        builder.setTitle("With EditText")
        val dialogLayout = inflater.inflate(com.qhackers.bucketshare.R.layout.alert_dialog_with_edittext, null)
        val editText  = dialogLayout.findViewById<EditText>(com.qhackers.bucketshare.R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") {
                dialogInterface, i ->
            Toast.makeText(this.context, "Successfully added item " + editText.text.toString(), Toast.LENGTH_SHORT).show()
            stringList.add(editText.text.toString())
            addItems()
        }
        builder.show()
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    fun addItems() {
        listAdapter.notifyDataSetChanged()
        Log.d("testing", stringList.size.toString())
    }
}
