package com.qhackers.bucketshare

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_resources.*


class MyListFragment : ListFragment() {

    private lateinit var listAdapter: ArrayAdapter<CharSequence>

    private var listItems: MutableList<String> = mutableListOf<String>()


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
            this.context!!, R.array.Resources, android.R.layout.simple_list_item_1, listItems
        )

        fab?.setOnClickListener {
            withEditText()
        }

        listAdapter = adapter
        listView.setOnItemClickListener{ _, _, position, _ ->
            val resArray = resources.getStringArray(R.array.Resources)
            Toast.makeText(activity, "Item: ${resArray[position]}", Toast.LENGTH_SHORT).show()
        }
    }

    fun withEditText() {
        val builder = AlertDialog.Builder(this.context)
        val inflater = layoutInflater
        builder.setTitle("With EditText")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_edittext, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i -> Toast.makeText(this.context, "EditText is " + editText.text.toString(), Toast.LENGTH_SHORT).show() }
        builder.show()
        listItems.add(editText.toString())
        addItems()
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    fun addItems() {
        listAdapter.notifyDataSetChanged()
    }
}
