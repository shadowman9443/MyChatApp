package com.example.aunogarafat.mychatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth?=null
    var user:FirebaseUser?=null
    var mAuthListener:FirebaseAuth.AuthStateListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        mAuthListener=FirebaseAuth.AuthStateListener {
            firebaseAuth: FirebaseAuth ->
            user=firebaseAuth.currentUser
            if(user!=null){
                startActivity(Intent(this,DashboardActivity::class.java))
            }
        }
        btnLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        btnCreateacnt.setOnClickListener {
          startActivity(Intent(this,CreateAccountActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if(mAuthListener!=null){
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }
}
