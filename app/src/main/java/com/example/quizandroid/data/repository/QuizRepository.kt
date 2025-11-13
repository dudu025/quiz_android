package com.example.quizandroid.data.repository

import android.util.Log
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

        // 1. Tenta fazer a chamada à API
        val response = apiService.getQuestions(
            amount = 10,
            category = categoryId
        )

        // 2. Verifica se a chamada de REDE falhou (ex: 404, 500)
        if (!response.isSuccessful) {
            throw Exception("Falha de rede ao buscar perguntas: ${response.message()}")
        }

        // 3. Verifica se o CORPO da resposta é nulo
        val body = response.body()
        if (body == null) {
            throw Exception("API retornou um corpo de resposta nulo.")
        }

        // 4. Verifica se a API retornou um código de erro (ex: 1 = Sem resultados)
        //    E se a lista de 'results' não é nula
        if (body.responseCode == 0 && body.results != null) {
            // SUCESSO! Retorna a lista de perguntas
            return body.results
        } else {
            // Se a API retornou um erro (responseCode != 0) ou uma lista nula,
            // apenas retorne uma lista vazia. Isso não é um crash,
            // é um resultado esperado (ex: "Não há perguntas para esta categoria").
            return emptyList()
        }
    } // Fim da getQuestionsFromapi
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