package com.example.quizandroid.data.repository

import android.content.Context
import androidx.room.Room
import com.example.quizandroid.data.local.AppDatabase
import com.example.quizandroid.data.local.entities.User
import com.example.quizandroid.data.UserDao

class UserRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "quiz_db"
    ).build()

    private val userDao: UserDao = db.userDao()

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun login(email: String, senha: String): User? {
        return userDao.login(email, senha)
    }
}
