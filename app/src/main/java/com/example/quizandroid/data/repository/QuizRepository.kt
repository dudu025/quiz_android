package com.example.quizandroid.data.repository

import com.example.quizandroid.data.UserDao
import com.example.quizandroid.data.local.ScoreDao
import com.example.quizandroid.data.local.entities.Score
import com.example.quizandroid.data.local.entities.User
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

    // TODO: Mudar no DAO para usar Flow<List<Score>>, como pede o PDF
    suspend fun getAllScores(): List<Score> {
        return scoreDao.getAllScores()
    }

    suspend fun deleteAllScores() {
        scoreDao.deleteAll()
    }
}