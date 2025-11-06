package com.example.quizandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.example.quizandroid.data.local.entities.User
import com.example.quizandroid.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // Mensagens de feedback (login, erros, etc.)
    val mensagem = mutableStateOf("")

    // Usuário logado atualmente (compartilhado entre as telas)
    private val _usuarioLogado = MutableStateFlow<User?>(null)
    val usuarioLogado = _usuarioLogado.asStateFlow()

    // -------------------- LOGIN --------------------
    fun login(email: String, senha: String, onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = repository.login(email, senha)
            if (user != null) {
                _usuarioLogado.emit(user)
                mensagem.value = "Login bem-sucedido!"
                onLoginSuccess()
            } else {
                mensagem.value = "Usuário ou senha incorretos."
            }
        }
    }

    // -------------------- CRIAR USUÁRIO --------------------
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

    // -------------------- CARREGAR USUÁRIO LOGADO --------------------
    fun carregarUsuarioLogado() {
        viewModelScope.launch {
            val usuario = repository.getUserLogado() // Método que você deve ter no seu repositório
            _usuarioLogado.emit(usuario)
        }
    }

    // -------------------- ATUALIZAR USUÁRIO --------------------
    fun atualizarUsuario(nome: String, email: String, senha: String) {
        viewModelScope.launch {
            _usuarioLogado.value?.let { usuarioAtual ->
                val usuarioAtualizado = usuarioAtual.copy(
                    nome = nome,
                    email = email,
                    senha = senha
                )
                repository.updateUser(usuarioAtualizado)
                _usuarioLogado.emit(usuarioAtualizado)
                mensagem.value = "Perfil atualizado com sucesso!"
            }
        }
    }

    // -------------------- DELETAR USUÁRIO --------------------
    fun deletarUsuarioLogado() {
        viewModelScope.launch {
            _usuarioLogado.value?.let {
                repository.deleteUser(it)
                _usuarioLogado.emit(null)
                mensagem.value = "Usuário excluído com sucesso!"
            }
        }
    }

    // -------------------- DESLOGAR --------------------
    fun deslogarUsuario() {
        viewModelScope.launch {
            _usuarioLogado.emit(null)
            mensagem.value = "Usuário deslogado."
        }
    }
}
