package com.example.electicsintlproject.acitvities

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.electicsintlproject.R
import com.example.electicsintlproject.utils.Constant.firebaseUserUniqueToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var databaseUsersReference: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    val context: Context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        databaseUsersReference = FirebaseDatabase.getInstance().getReference("Users")
        mAuth = FirebaseAuth.getInstance()

        bRegister.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.bRegister -> {
                addUser()
            }

        }
    }


    private fun addUser() {
        firebaseUserUniqueToken = FirebaseInstanceId.getInstance().getToken()!!
        val mobileNo = etMobileNumber.getText().toString().trim({ it <= ' ' })
        val DeviceToken = firebaseUserUniqueToken
        //check if the user has entered at least a mobile
        if (!TextUtils.isEmpty(mobileNo)) {
            val id = mAuth.currentUser!!.phoneNumber!!
            databaseUsersReference.child(id!!).child("mobileNumber").setValue(mobileNo)
            databaseUsersReference.child(id).child("deviceToken").setValue(DeviceToken)

            val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            loginIntent.putExtra("MobileNumber", mobileNo)
            Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_LONG).show()
            startActivity(loginIntent)
            finish()

        } else
            Toast.makeText(this@RegisterActivity, "Please enter at least a mobile number", Toast.LENGTH_LONG).show()
    }
}
