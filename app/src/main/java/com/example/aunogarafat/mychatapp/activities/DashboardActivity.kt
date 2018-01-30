package com.example.aunogarafat.mychatapp.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aunogarafat.mychatapp.R
import com.example.aunogarafat.mychatapp.adapters.SectionPagerAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar!!.title="Dashboard"
        var sectionPagedapter:SectionPagerAdapter?=null
        sectionPagedapter=SectionPagerAdapter(supportFragmentManager)
        dash_viewPager.adapter=sectionPagedapter
        main_tab_dash.setupWithViewPager(dash_viewPager)
        main_tab_dash.setTabTextColors(Color.BLUE,Color.WHITE)
        if(intent.extras!=null){
            var displayName=intent.extras.get("name")
            Toast.makeText(this,"Name"+displayName, Toast.LENGTH_LONG).show()
        }
    }
}
