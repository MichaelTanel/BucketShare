package com.qhackers.bucketshare

import android.widget.TextView
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class MyListAdapter(stringList: ArrayList<String>) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    private var mItems: ArrayList<String> = ArrayList<String>()

    fun MyListAdapter(items: ArrayList<String>) {
        mItems = items
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val context = p0.getContext()
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(com.qhackers.bucketshare.R.layout.list_item, p0, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return mItems?.size!!
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        // Get the data model based on position
        val item = mItems?.get(p1)

        // Set item views based on your views and data model
        val textView = p0.nameTextView
        textView.text = item
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder// We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
        (itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var nameTextView: TextView = itemView.findViewById(com.qhackers.bucketshare.R.id.tvListItem) as TextView

        // to access the context from any ViewHolder instance.
    }
}