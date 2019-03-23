package com.example.electicsintlproject.client

import com.example.electicsintlproject.model.FirebaseRequest
import com.example.electicsintlproject.model.FirebaseResponse
import com.example.electicsintlproject.utils.Constant
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface GetDataService {

@Headers("Authorization: "+Constant.firebaseProjectId,
    "Content-Type:application/json")
    @POST(EndPoints.SEND)
    fun firebaseCall(@Body firebaseRequest: FirebaseRequest): retrofit2.Call<FirebaseResponse>
}
