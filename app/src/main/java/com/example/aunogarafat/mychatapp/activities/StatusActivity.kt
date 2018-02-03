package com.example.aunogarafat.mychatapp.activities

import android.content.Intent
import com.example.aunogarafat.mychatapp.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {
    var mDatabase : DatabaseReference?=null
    var mCurrentUser: FirebaseUser?=null
    var mStoregRef: StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        supportActionBar!!.title="Status"

        if(intent.extras!=null){
            var oldStatus=intent.extras.get("status")
            oldStatustxt.text="Current Status:"+oldStatus.toString()
        }
        if(intent.extras.equals(null)){
            oldStatustxt.text= R.string.enter_txt.toString()
        }
        updateStatus.setOnClickListener {
            mCurrentUser=FirebaseAuth.getInstance().currentUser
            var userId=mCurrentUser!!.uid
            mDatabase=FirebaseDatabase.getInstance().reference
                    .child("Users")
                    .child(userId)
            var status = statusChangeet.text.toString().trim()
            if(!TextUtils.isEmpty(status)){
                mDatabase!!.child("status")
                        .setValue(status).addOnCompleteListener {
                        task: Task<Void> ->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Status Update Successfully", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,SettingsActivity::class.java))
                        }else{
                            Toast.makeText(this,"Status not Update Successfully", Toast.LENGTH_LONG).show()
                        }

                }
            }
        }
    }
}
