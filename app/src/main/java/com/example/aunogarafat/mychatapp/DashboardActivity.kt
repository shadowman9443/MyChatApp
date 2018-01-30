package com.example.aunogarafat.mychatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        if(intent.extras!=null){
            var displayName=intent.extras.get("name")
            Toast.makeText(this,"Name"+displayName, Toast.LENGTH_LONG).show()
        }
    }
}
