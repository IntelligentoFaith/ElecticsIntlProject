package com.example.electicsintlproject.acitvities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.electicsintlproject.R
import com.example.electicsintlproject.model.User
import com.example.electicsintlproject.utils.Constant
import com.example.electicsintlproject.utils.Constant.logMessage
import com.example.electicsintlproject.utils.Constant.loggedInUser
import com.example.electicsintlproject.utils.Constant.loggedInUserMobileNumber
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val RC_SIGN_IN = 123

    lateinit var mAuth: FirebaseAuth
    lateinit var usersDatabaseReference: DatabaseReference
    var refreshedToken: String = ""

    private val TAG = "#####LoginActivity#####"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        /*val userPassword: EditText  = findViewById(R.id.etPassword)
        val userMobilenumber: EditText = findViewById(R.id.etMobileNumberLOGIN)*/
        val loginButton: Button = findViewById(R.id.bLogin)


        loginButton.setOnClickListener(this)


        mAuth = FirebaseAuth.getInstance()
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")


        val currentUser = mAuth.currentUser
        logMessage("LOGIN ACTIVITY","current user = $currentUser")

        if (currentUser != null) {
            onSuccessProceed()
        }



    }
    override fun onClick(v: View?) {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    Arrays.asList(
                        //AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                        AuthUI.IdpConfig.PhoneBuilder().build()
                    ))
                .setTheme(R.style.AppTheme)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                onSuccessProceed()
            } else {
                // Sign in failed, check response for error code
                if (response != null) {
                    Constant.logMessage("signInWithCredential:failure", "${response.error}")
                }

                if (response != null) {
                    if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                        Toast.makeText(this, "no network connection", Toast.LENGTH_LONG).show()

                        return
                    }
                }

                if (response != null) {
                    Constant.logMessage("Sign-in error", "${response.error}")
                }

                Toast.makeText(this@LoginActivity, "An error occurred, please try again", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun onSuccessProceed() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val dashBoardIntent = Intent(applicationContext, AccountActivity::class.java)
            val usersDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
            val id = user.getPhoneNumber()


            // val preferences: SharedPreferences? = applicationContext.getSharedPreferences("RefreshedTokenPrefs",0)
            //refreshedToken = preferences!!.getString("RefreshedToken",refreshedToken)
            // Constant.logMessage("###LOGINACTIVITY-->onSuccessProceed###", "Refreshed token after login stored in shared Preferences is: $refreshedToken")

            //check if network is available code should go here before everything else
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Please wait...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            usersDatabaseReference.child(id!!).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val returnedUser = snapshot.getValue(User::class.java)

                        Log.w(TAG, "USER:" + "\n" + snapshot.getValue().toString())
                        loggedInUser = usersDatabaseReference.child(id).key.toString()
                        Log.w(TAG, "loggedInUserNumber: " + loggedInUser)

                        loggedInUserMobileNumber = returnedUser!!.mobileNumber
                        val devicetoken = returnedUser.deviceToken
                        Log.w(TAG, "loggedInUserName and devicetoken is:  $loggedInUserMobileNumber, $devicetoken")


                        if (loggedInUserMobileNumber != null && devicetoken != null) {
                            //updateDeviceToken
                            val deviceToken = FirebaseInstanceId.getInstance().getToken()
                            usersDatabaseReference.child(id).child("deviceToken").setValue(deviceToken)
                            Constant.logMessage("####logInActvity-->onActivityResult######", "login in success, device token set to $deviceToken")
                            startActivity(dashBoardIntent)
                            progressDialog.dismiss()
                            finish()

                        } else {
                            //user exists, is signed in but either a mobileNumber or device token are missing-->direct them to the register screen
                            progressDialog.dismiss()
                            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                            finish()
                        }

                    } else {
                        //user is authenticated but not registered-->go to register Screen
                        progressDialog.dismiss()
                        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                        finish()
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@LoginActivity, "error!" + databaseError.message, Toast.LENGTH_SHORT).show()
                }
            })


        }

    }

}
