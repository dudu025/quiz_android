package com.example.quizandroid.data.remote.dto

import com.squareup.moshi.Json

data class ApiResponse(
    @field:Json(name = "response_code")
    val responseCode: Int?,

    @field:Json(name="results")
    val results: List<QuestionResponse>?
)