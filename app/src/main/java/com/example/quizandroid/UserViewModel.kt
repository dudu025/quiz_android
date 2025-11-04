package com.example.quizandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.example.quizandroid.data.User
import com.example.quizandroid.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val mensagem = mutableStateOf("")

    fun login(email: String, senha: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = repository.login(email, senha)
            if (user != null) {
                mensagem.value = "Login bem-sucedido!"
                onLoginSuccess()
            } else {
                mensagem.value = "Usuário ou senha incorretos."
            }
        }
    }

    fun criarUsuario(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            val existente = repository.getUserByEmail(email)
            if (existente == null) {
                val novoUser = User(nome = nome, email = email, senha = senha)
                repository.insertUser(novoUser)
                mensagem.value = "Conta criada com sucesso!"
            } else {
                mensagem.value = "Email já cadastrado."
            }
        }
    }
}
