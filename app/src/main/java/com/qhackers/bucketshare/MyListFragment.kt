package com.qhackers.bucketshare

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import android.R
import android.util.Log
import android.widget.AdapterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
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
        println("DEBUG: show list fragment @@@@")
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        db.collection("lists")
            .document(auth.currentUser?.email!!)
            .get()
            .addOnSuccessListener { doc ->
                println("DEBUG: list data is: " + doc.data!!["content"])
                println("DEBUG:" + doc.data!!["content"] as? List<String>)
                stringList.clear()
                stringList.addAll(doc.data!!["content"] as List<String>)
                listAdapter.notifyDataSetChanged()
            }
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
        builder.setPositiveButton("OK") { dialogInterface, i ->
            Toast.makeText(this.context, "Successfully added item " + editText.text.toString(), Toast.LENGTH_SHORT).show()
            stringList.add(editText.text.toString())
            addItems()

            // write item to firestore
            val auth = FirebaseAuth.getInstance()
            val db = FirebaseFirestore.getInstance()
            db.collection("lists")
                .document(auth.currentUser?.email!!)
                .update("content", FieldValue.arrayUnion(editText.text.toString()))
        }
        builder.show()
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    fun addItems() {
        listAdapter.notifyDataSetChanged()
        Log.d("testing", stringList.size.toString())
    }
}
