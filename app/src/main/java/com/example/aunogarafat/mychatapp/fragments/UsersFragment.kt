package com.example.aunogarafat.mychatapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.aunogarafat.mychatapp.R
import com.example.aunogarafat.mychatapp.adapters.UsersAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_users.*


/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {

     var userDb:DatabaseReference?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        userDb=FirebaseDatabase.getInstance().reference.child("Users")
        friend_Recycler_view.setHasFixedSize(true)
        friend_Recycler_view.layoutManager=layoutManager
        friend_Recycler_view.adapter=UsersAdapter(userDb!!,context!!)



    }

}// Required empty public constructor
