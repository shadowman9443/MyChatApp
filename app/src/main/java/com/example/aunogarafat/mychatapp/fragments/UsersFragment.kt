package com.example.aunogarafat.mychatapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.aunogarafat.mychatapp.R


/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_users, container, false)
    }

}// Required empty public constructor
