package com.example.aunogarafat.mychatapp.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.aunogarafat.mychatapp.R
import com.example.aunogarafat.mychatapp.adapters.SectionPagerAdapter
import com.google.firebase.auth.FirebaseAuth
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         super.onOptionsItemSelected(item)
        if(item !=null){
            if(item.itemId==R.id.logout){
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            if(item.itemId==R.id.settings){
                startActivity(Intent(this,SettingsActivity::class.java))
            }
        }
        return true
    }
}
