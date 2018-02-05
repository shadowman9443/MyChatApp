package com.example.aunogarafat.mychatapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.aunogarafat.mychatapp.R
import com.example.aunogarafat.mychatapp.models.Friendlymessage
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {


    var userid:String?=null
    var fdatabaseReference:DatabaseReference?=null
    var duser:FirebaseUser?= null
    var linearlayoutmanagaer:LinearLayoutManager?=null
    var firbaseAdapter:FirebaseRecyclerAdapter<Friendlymessage,MessageViewholder>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        duser = FirebaseAuth.getInstance().currentUser
        userid=intent.extras.getString("userId")
        linearlayoutmanagaer= LinearLayoutManager(this)
        linearlayoutmanagaer!!.stackFromEnd = true

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fdatabaseReference=FirebaseDatabase.getInstance().reference


        firbaseAdapter = object : FirebaseRecyclerAdapter<Friendlymessage,
                MessageViewholder>(
                Friendlymessage::class.java,
                R.layout.item_message,
                MessageViewholder::class.java,
                fdatabaseReference!!.child("messages")) {

            override fun populateViewHolder(viewHolder: MessageViewholder?, friendlyMessage: Friendlymessage?, position: Int) {

                if (friendlyMessage!!.text != null) {
                    viewHolder!!.bindView(friendlyMessage)

                    var currentUserId = duser!!.uid

                    var isMe: Boolean = friendlyMessage!!.id!!.equals(currentUserId)

                    if (isMe) {
                        //Me to the right side
                        viewHolder.profileImageViewRight!!.visibility = View.VISIBLE
                        viewHolder.profileImageView!!.visibility = View.GONE
                        viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                        viewHolder.messengerTextView!!.gravity = (Gravity.CENTER_VERTICAL or  Gravity.RIGHT)


                        //Get imageUrl for me!
                        fdatabaseReference!!.child("Users")
                                .child(currentUserId)
                                .addValueEventListener( object: ValueEventListener {
                                    override fun onDataChange(data: DataSnapshot?) {

                                        var imageUrl = data!!.child("thumb_image").value.toString()
                                        var displayName = data!!.child("display_name").value

                                        viewHolder.messengerTextView!!.text =
                                                "I wrote..."

                                        Picasso.with(viewHolder.profileImageView!!.context)
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_img)
                                                .into(viewHolder.profileImageViewRight)


                                    }

                                    override fun onCancelled(error: DatabaseError?) {

                                    }
                                })

                    }else {
                        //the other person show imageview to the left side

                        viewHolder.profileImageViewRight!!.visibility = View.GONE
                        viewHolder.profileImageView!!.visibility = View.VISIBLE
                        viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)
                        viewHolder.messengerTextView!!.gravity = (Gravity.CENTER_VERTICAL or  Gravity.LEFT)


                        //Get imageUrl for me!
                        fdatabaseReference!!.child("Users")
                                .child(userid)
                                .addValueEventListener( object: ValueEventListener{
                                    override fun onDataChange(data: DataSnapshot?) {

                                        var imageUrl = data!!.child("thumb_image").value.toString()
                                        var displayName = data!!.child("display_name").value.toString()

                                        viewHolder.messengerTextView!!.text =
                                                "$displayName wrote..."

                                        Picasso.with(viewHolder.profileImageView!!.context)
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_img)
                                                .into(viewHolder.profileImageView)


                                    }

                                    override fun onCancelled(error: DatabaseError?) {

                                    }
                                })


                    }

                }
            }

        }
        messageRecyclerView.layoutManager = linearlayoutmanagaer
        messageRecyclerView.adapter = firbaseAdapter

    }
    class MessageViewholder(itemView:View): RecyclerView.ViewHolder(itemView){
        var messageTextView: TextView? = null
        var messengerTextView: TextView? = null
        var profileImageView: CircleImageView? = null
        var profileImageViewRight: CircleImageView? = null
        fun bindView(frindlyMessage:Friendlymessage?){
            messageTextView = itemView.findViewById(R.id.messageTextview)
            messengerTextView = itemView.findViewById(R.id.messengerTextview)
            profileImageView = itemView.findViewById(R.id.messengerImageView)
            profileImageViewRight = itemView.findViewById(R.id.messengerImageViewRight)

            messengerTextView!!.text = frindlyMessage!!.name
            messageTextView!!.text = frindlyMessage!!.text
        }
    }
}
