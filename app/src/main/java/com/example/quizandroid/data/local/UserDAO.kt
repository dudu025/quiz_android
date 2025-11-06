package com.example.quizandroid.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizandroid.data.local.entities.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun login(email: String, senha: String): User?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}
