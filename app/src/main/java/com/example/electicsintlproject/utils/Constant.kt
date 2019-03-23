package com.example.electicsintlproject.utils

import android.net.Uri
import android.util.Log

object Constant {

    fun logMessage(key: String, value: String) {
        Log.e(key, value)
    }

    var firebaseUserUniqueToken=""
    const val firebaseProjectId="key="+"AIzaSyCQ619taB0IbO6Bj6G9cDKZWachwGPw4cM"
    var loggedInUser = ""
    var loggedInUserMobileNumber: String? = null

}