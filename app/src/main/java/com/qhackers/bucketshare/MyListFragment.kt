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
import kotlinx.android.synthetic.main.fragment_my_list.*

class MyListFragment : ListFragment(), AdapterView.OnItemClickListener {

    var stringList: ArrayList<String> = ArrayList()
    private lateinit var listAdapter: ArrayAdapter<String>

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
        listAdapter = ArrayAdapter(context, com.qhackers.bucketshare.R.layout.list_item, stringList)
        list.adapter = listAdapter
        list.onItemClickListener = this@MyListFragment

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
