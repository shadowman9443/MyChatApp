package com.example.aunogarafat.mychatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {
    var mAuth:FirebaseAuth ?=null
    var database:DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        mAuth=FirebaseAuth.getInstance()

        accountCreateActBtn.setOnClickListener {
            var name=accountDisplayNameEt.text.toString().trim()
            var email=accountEmailEt.text.toString().trim()
            var password=accountPasswordEt.text.toString().trim()

            if(!TextUtils.isEmpty(name)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                createAccount(email,password,name)
            }else{
                Toast.makeText(this,"Please fill out all fields",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun createAccount(email:String,password:String,displayName:String){
        mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    task:Task<AuthResult> ->
                        if(task.isSuccessful){

                                var currentUser=mAuth!!.currentUser
                                var userId=currentUser!!.uid

                                database=FirebaseDatabase.getInstance().reference
                                        .child("Users").child(userId)


                                var userobject=HashMap<String,String>()
                                userobject.put("display_name",displayName)
                                userobject.put("status","hello there")
                                userobject.put("image","default")
                                userobject.put("thumb_image","default")

                                database!!.setValue(userobject)
                                        .addOnCompleteListener {
                                            task:Task<Void> ->
                                            if(task.isSuccessful) {
                                                Toast.makeText(this,"User Created Successfully",Toast.LENGTH_LONG).show()

                                                var dashBoardIntent=Intent(this,DashboardActivity::class.java)
                                                    dashBoardIntent.putExtra("name",displayName)
                                                startActivity(dashBoardIntent)
                                                finish()
                                            }else {
                                                Toast.makeText(this,"User  not Created ",Toast.LENGTH_LONG).show()
                                            }
                                        }

                        }else{
                            Toast.makeText(this,"Error"+task.exception,Toast.LENGTH_LONG).show()
                        }

                    }
                }
    }

