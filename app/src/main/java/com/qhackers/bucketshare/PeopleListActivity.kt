package com.qhackers.bucketshare

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.content_people_list.*


class PeopleListActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    var nameList: ArrayList<String> = ArrayList()
    private lateinit var listAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nameList.clear()
        listAdapter = ArrayAdapter(this, com.qhackers.bucketshare.R.layout.list_item, nameList)
        peopleList.adapter = listAdapter
        peopleList.onItemClickListener = this@PeopleListActivity
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selected = (view?.findViewById(R.id.tvItem) as TextView).text.toString()
        Toast.makeText(this, selected, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

}
