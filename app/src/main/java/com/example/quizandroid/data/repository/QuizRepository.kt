package com.example.quizandroid.data.repository

import com.example.quizandroid.data.UserDao
import com.example.quizandroid.data.local.ScoreDao
import com.example.quizandroid.data.remote.OpenTdbApiService
import com.example.quizandroid.data.remote.dto.QuestionResponse

class QuizRepository (
    private val apiService: OpenTdbApiService,
    private val scoreDao : ScoreDao,
    private val userDao: UserDao
) {
    suspend fun getQuestionsFromapi(categoryId: Int): List<QuestionResponse> {
        return try {
            val response = apiService.getQuestions(
                amount = 10,
                category = categoryId
            )
            if (response.isSuccessful && response.body() !=null) {
                response.body()!!.results
            } else {
                emptyList()
            }
        } catch (e:Exception) {
            println("Erro ao buscar perguntas : ${e.message}")
            emptyList()
        }
    }
}