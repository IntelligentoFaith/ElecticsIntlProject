package com.example.electicsintlproject.model



data class FirebaseRequest(
        var data: notificationData,
        var registration_ids: ArrayList<String>?
)

data class notificationData(
        var title: String,
        var body: String,
        var sound: String
)
