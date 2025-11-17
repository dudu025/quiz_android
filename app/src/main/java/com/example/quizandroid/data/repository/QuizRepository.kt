package com.example.quizandroid.data.repository

import android.util.Log
import com.example.quizandroid.data.UserDao
import com.example.quizandroid.data.local.ScoreDao
import com.example.quizandroid.data.local.entities.Score
import com.example.quizandroid.data.local.entities.User
import com.example.quizandroid.data.remote.OpenTdbApiService
import com.example.quizandroid.data.remote.dto.QuestionResponse
import kotlinx.coroutines.flow.Flow

class QuizRepository (
    private val apiService: OpenTdbApiService,
    private val scoreDao : ScoreDao,
    private val userDao: UserDao
) {

    // --- ESTA FUNÇÃO FOI MODIFICADA ---
    suspend fun getQuestionsFromapi(category: Int): List<QuestionResponse>? {
        // Envolve a chamada de rede em um try-catch (Requisito 4 do PDF)
        return try {
            val response = apiService.getQuestions(
                amount = 10,
                category = category
            )
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.results
            } else {
                emptyList()
            }
        } catch (e: Exception) { // Captura erros (ex: sem internet)
            println("Erro ao buscar perguntas : ${e.message}")
            emptyList() // Retorna uma lista vazia em vez de crashar
        }
    }


    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun login(email: String, senha: String): User? {
        return userDao.login(email, senha)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

    suspend fun getUserLogado(): User? {
        return userDao.getUserLogado()
    }

    suspend fun setUserLoggedIn(email: String, isLoggedIn: Boolean) {
        // Lógica melhorada: buscar por ID e atualizar
        val user = userDao.getUserByEmail(email) // <- Usa a nova função do DAO
        user?.let {
            val updatedUser = it.copy(isLoggedIn = isLoggedIn)
            userDao.update(updatedUser)
        }
    }

    // --- Funções de Score (Room) ---
    // (Funções para o Ranking/Histórico)

    suspend fun insertScore(score: Score) {
        scoreDao.insertScore(score)
    }

    fun getAllScores(): Flow<List<Score>> {
        return scoreDao.getAllScores()
    }

    suspend fun deleteAllScores() {
        scoreDao.deleteAll()
    }

    suspend fun deleteScore(score: Score) {
        scoreDao.deleteScore(score)
    }
}