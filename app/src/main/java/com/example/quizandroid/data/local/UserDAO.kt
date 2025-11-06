package com.example.quizandroid.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.quizandroid.data.local.entities.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    // Método de login (verifica email e senha)
    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun login(email: String, senha: String): User?

    // Método para buscar um usuário por e-mail
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    // Método para buscar o usuário logado (AQUI VOCÊ PODE USAR O ID OU OUTRO CRITÉRIO)
    @Query("SELECT * FROM usuarios WHERE isLoggedIn = 1 LIMIT 1") // Adicione esse campo 'isLoggedIn' no modelo de dados User
    suspend fun getUserLogado(): User?
}
