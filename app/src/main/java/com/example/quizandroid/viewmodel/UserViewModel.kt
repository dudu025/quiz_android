package com.example.quizandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizandroid.data.local.entities.User
import com.example.quizandroid.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update // Verifique se esta importação existe
import kotlinx.coroutines.launch

// 1. Definição do UiState para a tela de Login
data class UserUiState(
    val isLoading: Boolean = false,
    val mensagem: String = "",
    val loginSucesso: Boolean = false // Sinal para a UI navegar
)

class UserViewModel(private val repository: QuizRepository) : ViewModel() {

    // 2. Substituir o 'mutableStateOf' pelo 'StateFlow'
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    // -------------------- LOGIN --------------------
    // 3. A função 'login' agora atualiza o UiState e não recebe mais o 'onLoginSuccess'
    fun login(email: String, senha: String) {
        _uiState.update { it.copy(isLoading = true, mensagem = "") } // Limpa a msg e mostra loading
        val emailNormalizado = email.trim().lowercase()

        viewModelScope.launch {
            val user = repository.login(emailNormalizado, senha)
            if (user != null) {
                repository.setUserLoggedIn(user.email, true)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagem = "Login bem-sucedido!",
                        loginSucesso = true // Sinaliza sucesso para a UI
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagem = "Usuário ou senha incorretos."
                    )
                }
            }
        }
    }

    // -------------------- CRIAR USUÁRIO --------------------
    // 4. A função 'criarUsuario' também atualiza o UiState
    fun criarUsuario(nome: String, email: String, senha: String) {
        _uiState.update { it.copy(isLoading = true, mensagem = "") }
        val emailNormalizado = email.trim().lowercase()

        viewModelScope.launch {
            val existente = repository.getUserByEmail(emailNormalizado)
            if (existente == null) {
                // Pega o campo 'isAdmin' da entidade User.kt
                val novoUser = User(nome = nome, email = emailNormalizado, senha = senha, isAdmin = false)
                repository.insertUser(novoUser)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagem = "Conta criada com sucesso!"
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagem = "Email já cadastrado."
                    )
                }
            }
        }
    }

    // 5. FUNÇÕES DUPLICADAS REMOVIDAS
    // (As funções carregarUsuarioLogado, atualizarUsuario, etc.
    //  foram removidas daqui pois pertencem ao ProfileViewModel)
}