package com.example.quizandroid
import android.app.Application
import androidx.room.Room
import com.example.quizandroid.data.local.AppDatabase
import com.example.quizandroid.data.remote.OpenTdbApiService
import com.example.quizandroid.data.repository.QuizRepository

class QuizApplication : Application() {

    // 1. Instância "preguiçosa" do Banco de Dados
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "quiz_db"
        ).build()
    }

    // 2. Instância "preguiçosa" do Serviço de API
    private val apiService: OpenTdbApiService by lazy {
        OpenTdbApiService.create()
    }

    // 3. Instância "preguiçosa" do Repositório ÚNICO
    //    (Observe como injetamos os DAOs e a API nele)
    val quizRepository: QuizRepository by lazy {
        QuizRepository(
            apiService = apiService,
            scoreDao = database.scoreDao(),
            userDao = database.userDao()
        )
    }
}