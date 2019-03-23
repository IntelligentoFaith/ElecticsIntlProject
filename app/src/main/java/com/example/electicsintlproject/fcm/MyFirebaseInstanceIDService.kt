package com.example.electicsintlproject.fcm

import android.content.SharedPreferences
import com.example.electicsintlproject.utils.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    lateinit var userid: String

    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token!!
        Constant.logMessage("###MyFirebaseInstanceIDService-->onTokenRefresh###","REFRESHED TOKEN: $refreshedToken")

        if (FirebaseAuth.getInstance().currentUser !=null){
            userid = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!
            sendRefreshedTokentoDatabase(refreshedToken)
            Constant.logMessage("######MyFirebaseInstanceIDService-->onTokenRefresh###", "SAVING REFRESHED TOKEN IN database SUCCESS! $refreshedToken")

        }else{
            val preferences:SharedPreferences? = applicationContext.getSharedPreferences("RefreshedTokenPrefs",0)
            val editor:SharedPreferences.Editor? = preferences?.edit()
            editor?.putString("RefreshedToken", refreshedToken)
            editor?.apply()
            Constant.logMessage("######MyFirebaseInstanceIDService-->onTokenRefresh###", "SAVING REFRESHED TOKEN IN SHAREDPREFS SUCCESS!")
        }

    }

    private fun sendRefreshedTokentoDatabase(refreshedToken: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val deviceTokenRef = databaseReference.child(userid).child("deviceToken")

        deviceTokenRef.setValue(refreshedToken)
    }

}