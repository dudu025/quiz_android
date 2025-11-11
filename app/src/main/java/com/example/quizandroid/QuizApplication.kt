package com.example.quizandroid

import android.app.Application
import androidx.room.Room
import com.example.quizandroid.data.local.AppDatabase
import com.example.quizandroid.data.remote.OpenTdbApiService
import com.example.quizandroid.data.repository.QuizRepository

class QuizApplication : Application() {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "quiz_db"
        )
            .fallbackToDestructiveMigration() // 🔹 ADICIONE ESTA LINHA
            .build()
    }

    private val apiService: OpenTdbApiService by lazy {
        OpenTdbApiService.create()
    }

    val quizRepository: QuizRepository by lazy {
        QuizRepository(
            apiService = apiService,
            scoreDao = database.scoreDao(),
            userDao = database.userDao()
        )
    }
}
