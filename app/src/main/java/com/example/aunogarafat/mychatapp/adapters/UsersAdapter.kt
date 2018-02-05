package com.example.aunogarafat.mychatapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.aunogarafat.mychatapp.R
import com.example.aunogarafat.mychatapp.activities.ChatActivity
import com.example.aunogarafat.mychatapp.activities.Profilectivity
import com.example.aunogarafat.mychatapp.models.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by aunogarafat on 2/3/18.
 * Project name MyChatApp
 */
class UsersAdapter(databaseQuer:DatabaseReference,var context:Context):
        FirebaseRecyclerAdapter<User,UsersAdapter.ViewHolder>
         (User::class.java, R.layout.users_row,UsersAdapter.ViewHolder::class.java,databaseQuer){
    override fun populateViewHolder(viewHolder: UsersAdapter.ViewHolder?, user: User?, position: Int) {

        var userId=getRef(position).key
        viewHolder!!.bindView(user!!,context)
        viewHolder.itemView.setOnClickListener {
           var option= arrayOf("Open Profile","Send message")
            var builder=AlertDialog.Builder(context)
            builder.setTitle("Select option")
            builder.setItems(option,DialogInterface.OnClickListener { dialogInterface, i ->
                var username=viewHolder.userNameTxt
                var status=viewHolder.userStatusTxt
                var imagelink=viewHolder.userProfilePicLink
                if(i==0){
                    var profileIntent = Intent(context, Profilectivity::class.java)
                    profileIntent.putExtra("userId", userId)
                    context.startActivity(profileIntent)
                }else{
                    var chatIntent = Intent(context, ChatActivity::class.java)
                    chatIntent.putExtra("userId", userId)
                    chatIntent.putExtra("username", username)
                    chatIntent.putExtra("status", status)
                    chatIntent.putExtra("imagelink", imagelink)
                    chatIntent.putExtra("userId", userId)
                    context.startActivity(chatIntent)
                }
            })
            builder.show()

        }

    }


    class ViewHolder(itmeView:View) :RecyclerView.ViewHolder(itmeView){
        var userNameTxt: String? = null
        var userStatusTxt: String? = null
        var userProfilePicLink: String? = null
        fun bindView(user: User,context: Context){
            var userName = itemView.findViewById<TextView>(R.id.userName)
            var userStatus = itemView.findViewById<TextView>(R.id.userStatus)
            var userProfilePic = itemView.findViewById<CircleImageView>(R.id.usersProfile)

            //set the strings so we can pass in the intent
            userNameTxt = user.display_name
            userStatusTxt = user.status
            userProfilePicLink = user.thumb_image

            userName.text = user.display_name
            userStatus.text = user.status

            Picasso.with(context)
                    .load(userProfilePicLink)
                    .placeholder(R.drawable.profile_img)
                    .into(userProfilePic)
        }
    }
}