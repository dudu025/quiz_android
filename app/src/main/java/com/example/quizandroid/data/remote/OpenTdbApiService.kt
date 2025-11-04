package com.example.quizandroid.data.remote

import com.example.quizandroid.data.remote.dto.ApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenTdbApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 10,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String = "medium",
        @Query("type") type: String = "multiple"
    ): Response<ApiResponse>

    companion object {
        private const val BASE_URL = "https://opentdb.com/"
        fun create(): OpenTdbApiService{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(OpenTdbApiService::class.java)
        }
    }
}