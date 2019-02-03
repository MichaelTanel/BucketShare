package com.qhackers.bucketshare

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_my_list.*
import android.widget.TextView



class MyListFragment : ListFragment(), AdapterView.OnItemClickListener {

    var stringList: ArrayList<String> = ArrayList()
    private lateinit var listAdapter: ArrayAdapter<String>

    companion object {
        fun newInstance(): MyListFragment {
            return MyListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // pull data from firestore and display in list
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

        list.setOnItemLongClickListener { parent, view, position, id ->
            if (view is TextView) {
                val oldText: String = view.text.toString()
                launchEditDialog(position, oldText)
            }

            true
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val intent = Intent(activity, PeopleListActivity::class.java)
        val selected = (view?.findViewById(R.id.tvItem) as TextView).text.toString()
        intent.putExtra("activity", selected)
        startActivity(intent)
    }

    fun launchEditDialog(position: Int, oldText: String) {
        val builder = AlertDialog.Builder(this.context)
        val inflater = layoutInflater
        builder.setTitle("With EditText")
        val dialogLayout = inflater.inflate(com.qhackers.bucketshare.R.layout.alert_dialog_with_edittext, null)
        val editText  = dialogLayout.findViewById<EditText>(com.qhackers.bucketshare.R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            Toast.makeText(this.context, "Successfully added item " + editText.text.toString(), Toast.LENGTH_SHORT).show()
            stringList[position] = editText.text.toString()
            addItems()

            // write item to firestore
            val auth = FirebaseAuth.getInstance()
            val db = FirebaseFirestore.getInstance()
            db.collection("lists")
                .document(auth.currentUser?.email!!)
                .update("content", FieldValue.arrayRemove(oldText))
            db.collection("lists")
                .document(auth.currentUser?.email!!)
                .update("content", FieldValue.arrayUnion(editText.text.toString()))
        }
        builder.show()
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
