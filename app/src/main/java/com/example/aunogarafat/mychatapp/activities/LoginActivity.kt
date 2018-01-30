package com.example.aunogarafat.mychatapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.aunogarafat.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var database: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        loginButtonId.setOnClickListener {

            var email = loginEmailE.text.toString().trim()
            var password = loginPasswordEt.text.toString().trim()

            if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                createAccount(email, password)
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        firebaseUser = task.result.user
                        var displayName=email.split("@")[0]
                        var currentUser = mAuth!!.currentUser


                        var dashBoardIntent = Intent(this, DashboardActivity::class.java)
                        dashBoardIntent.putExtra("name", displayName)
                        startActivity(dashBoardIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Errr"+task.exception, Toast.LENGTH_LONG).show()


                    }

                }
    }
}
