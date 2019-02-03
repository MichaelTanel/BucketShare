package com.qhackers.bucketshare

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_people_list.*


class PeopleListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    var nameList: ArrayList<String> = ArrayList()
    private lateinit var listAdapter: ArrayAdapter<String>
    private lateinit var intentExtra: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intentExtra = intent.getStringExtra("activity")

        nameList.clear()
        listAdapter = ArrayAdapter(this, com.qhackers.bucketshare.R.layout.list_item, nameList)
        peopleList.adapter = listAdapter
        peopleList.onItemClickListener = this@PeopleListActivity

        fetchData()
    }

    private fun fetchData() {
        val db = FirebaseFirestore.getInstance()
        val mAuth = FirebaseAuth.getInstance()

        // get list of people who have this item query
        db.collection("lists")
            .whereArrayContains("content", intentExtra)
            .get()
            .addOnSuccessListener { result ->
                if (result != null) {
                    result.documents
                        .filter { doc ->
                            doc.id != mAuth.currentUser?.email
                        }
                        .forEach { doc ->
                            nameList.add(doc.id)
                        }
                    listAdapter.notifyDataSetChanged()
                } else {
                    println("DEBUG: No people results")
                }
            }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selected = (view?.findViewById(R.id.tvItem) as TextView).text.toString()
        val intent = Intent(this, MessageListActivity::class.java)
        intent.putExtra("email", selected)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

}
