package com.example.quizandroid.viewmodel // Verifique se o package está correto

import android.text.Html // Importe isso para decodificar o texto
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quizandroid.data.remote.dto.QuestionResponse // Importe seu DTO
import com.example.quizandroid.data.repository.QuizRepository // Importe seu Repositório
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Define um modelo de Pergunta "limpo" para a tela
data class QuizQuestion(
    val text: String,
    val options: List<String>,
    val correctAnswer: String
)

// 2. Define o "estado" da tela de Quiz (É AQUI QUE O .error MORA)
data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val isAnswerSelected: Boolean = false,
    val selectedAnswer: String = "",
    val isQuizFinished: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null // <-- O campo que estava dando erro
) {
    // Helper para pegar a pergunta atual
    val currentQuestion: QuizQuestion?
        get() = questions.getOrNull(currentQuestionIndex)

    // Helper para o placar em texto (ex: "1/10")
    val progressText: String
        get() = "${currentQuestionIndex + 1} / ${questions.size}"

    // Helper para o placar final (ex: "8-10" para a URL)
    val finalScoreText: String
        get() = "$score-${questions.size}"
}

// 3. O ViewModel
class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    // Função para carregar as perguntas da API
    fun loadQuestions(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Busca as perguntas do seu repositório
                val questionsFromApi = repository.getQuestionsFromapi(categoryId)
                // Converte e limpa as perguntas (API manda texto em HTML)
                val cleanQuestions = questionsFromApi.map { it.toQuizQuestion() }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        questions = cleanQuestions,
                        currentQuestionIndex = 0,
                        score = 0,
                        isQuizFinished = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Falha ao carregar perguntas.") }
            }
        }
    }

    // Função chamada quando o usuário clica em uma resposta
    fun selectAnswer(selectedAnswer: String) {
        val state = _uiState.value
        if (state.isAnswerSelected || state.isQuizFinished) return // Já respondeu

        val currentQuestion = state.currentQuestion ?: return
        val isCorrect = (selectedAnswer == currentQuestion.correctAnswer)

        _uiState.update {
            it.copy(
                isAnswerSelected = true,
                selectedAnswer = selectedAnswer,
                score = if (isCorrect) it.score + 1 else it.score
            )
        }
    }

    // Função para ir para a próxima pergunta
    fun nextQuestion() {
        val state = _uiState.value
        val nextIndex = state.currentQuestionIndex + 1

        if (nextIndex >= state.questions.size) {
            // Acabou o quiz
            _uiState.update { it.copy(isQuizFinished = true) }
        } else {
            // Próxima pergunta
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    isAnswerSelected = false,
                    selectedAnswer = ""
                )
            }
        }
    }
}

// Função helper para "limpar" a pergunta da API
private fun QuestionResponse.toQuizQuestion(): QuizQuestion {
    // A API manda a resposta correta e as erradas em listas separadas
    // Vamos juntar, embaralhar e limpar o texto (HTML)
    val options = (this.incorrectAnswers + this.correctAnswer).shuffled()

    return QuizQuestion(
        text = Html.fromHtml(this.question, Html.FROM_HTML_MODE_LEGACY).toString(),
        options = options.map { Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString() },
        correctAnswer = Html.fromHtml(this.correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()
    )
}

// Factory para o QuizViewModel (igual às suas outras factories)
class QuizViewModelFactory(
    private val repository: QuizRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}