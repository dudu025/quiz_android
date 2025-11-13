package com.example.quizandroid.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizandroid.data.local.entities.User
import com.example.quizandroid.data.remote.dto.QuestionResponse
import com.example.quizandroid.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel(private val repository: QuizRepository) : ViewModel() {

    val question = MutableStateFlow<List<QuestionResponse>?>(null)

    fun carregarQuestion(categoryId: Int) {
        viewModelScope.launch {
            val resp = repository.getQuestionsFromapi(categoryId) // Método que você deve ter no seu repositório
            question.emit(resp)
        }
    }
}