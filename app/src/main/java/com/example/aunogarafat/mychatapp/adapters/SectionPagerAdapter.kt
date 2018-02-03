package com.example.aunogarafat.mychatapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.aunogarafat.mychatapp.fragments.ChatsFragment
import com.example.aunogarafat.mychatapp.fragments.UsersFragment

/**
 * Created by aunogarafat on 1/31/18.
 * Project name MyChatApp
 */
class SectionPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
       when(position){
           0->{
               return UsersFragment()
           }
           1->{
               return ChatsFragment()
           }
       }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->{
                return "Friends"
            }
            1->{
                return "Chats"
            }
        }
        return super.getPageTitle(position)
    }
}