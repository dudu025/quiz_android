package com.example.quizandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizandroid.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val nome: String = "",
    val email: String = "",
    val mensagem: String = "",
    val logoutSucesso: Boolean = false
)

class ProfileViewModel (private val repository: QuizRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init{
        loadUsuarioLogado()
    }

    public fun loadUsuarioLogado(){
        viewModelScope.launch{
            _uiState.update { it.copy(isLoading = true) }
            val user = repository.getUserLogado()
            if( user != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        nome = user.nome,
                        email = user.email
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, mensagem = "Erro ao carregar o Perfil.") }
            }
        }
    }
    fun SalvarPerfil(novoNome: String, novoEmail: String,novaSenha: String ){
        viewModelScope.launch {
            val userLogado = repository.getUserLogado()
            if (userLogado != null) {
                val usuarioAtualizado = userLogado.copy(
                    nome = novoNome,
                    email = novoEmail,
                    senha = if (novaSenha.isBlank()) userLogado.senha else novaSenha
                )
                repository.updateUser(usuarioAtualizado)
                _uiState.update { it.copy(mensagem = "Perfil salvo com sucesso!") }
            }
        }
    }

    fun deslogarUsuario() {
        viewModelScope.launch {
            val user = repository.getUserLogado()
            if (user != null) {
                repository.setUserLoggedIn(user.email, false)
                _uiState.update { it.copy(logoutSucesso = true) }
            }
        }
    }

    fun deletarUsuarioLogado() {
        viewModelScope.launch {
            val user = repository.getUserLogado()
            if (user != null) {
                repository.deleteUser(user)
                _uiState.update { it.copy(logoutSucesso = true) }
            }
        }
    }

}