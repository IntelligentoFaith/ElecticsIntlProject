package com.example.electicsintlproject.model

class User{
    var mobileNumber: String? = null
    var deviceToken: String? =null



    constructor(){}


    constructor(mobilenumber: String?,deviceToken: String?){
        this.mobileNumber = mobilenumber

        this.deviceToken = deviceToken

    }
    }
