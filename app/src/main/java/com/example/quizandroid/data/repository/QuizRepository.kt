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

    // --- ESTA FUNÇÃO FOI MODIFICADA ---
    suspend fun getQuestionsFromapi(categoryId: Int): List<QuestionResponse> {
        // O 'try-catch' foi movido para o ViewModel,
        // aqui vamos deixar o erro "explodir" se acontecer

        val response = apiService.getQuestions(
            amount = 10,
            category = categoryId
        )

        if (response.isSuccessful && response.body() != null) {
            // Sucesso! Retorna os resultados
            return response.body()!!.results
        } else {
            // Falha na API: lança um erro que o ViewModel vai pegar
            throw Exception("Falha na API: ${response.message()}")
        }
    }
    // --- FIM DA MODIFICAÇÃO ---


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