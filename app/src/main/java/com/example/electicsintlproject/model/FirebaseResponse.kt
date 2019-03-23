package com.example.electicsintlproject.model

data class FirebaseResponse(
        val multicast_id: Long,
        val success: Int,
        val failure: Int,
        val canonical_ids: Int,
        val results: List<Result>
)

data class Result(
        val message_id: String
)