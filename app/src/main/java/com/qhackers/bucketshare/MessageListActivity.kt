package com.qhackers.bucketshare

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sendbird.android.BaseMessage


class MessageListActivity : AppCompatActivity() {

    private lateinit var mMessageRecycler: RecyclerView
    private lateinit var mMessageAdapter: MessageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        mMessageRecycler = findViewById(R.id.recyclerview_message_list)
        val messageList = ArrayList<BaseMessage>()
        mMessageAdapter = MessageListAdapter(this, messageList)
        mMessageRecycler.layoutManager = LinearLayoutManager(this)
        mMessageRecycler.adapter = mMessageAdapter;
    }
}
