package com.qhackers.bucketshare

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sendbird.android.BaseMessage
import com.sendbird.android.UserMessage
import com.sendbird.android.SendBird
import android.view.LayoutInflater

class MessageListAdapter(val context: Context, val messageList: List<BaseMessage>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View

        if (viewType === VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_sent, parent, false)
            return SentMessageHolder(view)
        } else {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_received, parent, false)
            return ReceivedMessageHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position] as UserMessage

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position] as UserMessage

        return if (message.sender.userId == SendBird.getCurrentUser().userId) {
            // If the current user is the sender of the message
            VIEW_TYPE_MESSAGE_SENT
        } else {
            // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    private inner class SentMessageHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var messageText: TextView
        internal var timeText: TextView

        init {

            messageText = itemView.findViewById(R.id.text_message_body) as TextView
            timeText = itemView.findViewById(R.id.text_message_time) as TextView
        }

        internal fun bind(message: UserMessage) {
            messageText.text = message.message

            // Format the stored timestamp into a readable String using method.
            timeText.text = DateUtils.formatDateTime(context, message.createdAt, 1)
        }
    }

    private inner class ReceivedMessageHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var messageText: TextView
        internal var timeText: TextView
        internal var nameText: TextView
        internal var profileImage: ImageView

        init {
            messageText = itemView.findViewById(R.id.text_message_body)
            timeText = itemView.findViewById(R.id.text_message_time)
            nameText = itemView.findViewById(R.id.text_message_name)
            profileImage = itemView.findViewById(R.id.image_message_profile) as ImageView
        }

        internal fun bind(message: UserMessage) {
            messageText.text = message.message

            // Format the stored timestamp into a readable String using method.
            timeText.text = DateUtils.formatDateTime(context, message.createdAt, 1)
            nameText.text = message.sender.nickname
        }
    }
}