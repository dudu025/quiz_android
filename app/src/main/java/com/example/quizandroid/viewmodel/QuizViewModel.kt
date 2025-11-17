package com.example.quizandroid.viewmodel // Verifique se o package está correto

import android.text.Html // Importe isso para decodificar o texto
import android.util.Log
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
import org.jsoup.Jsoup

// 1. Define um modelo de Pergunta "limpo" para a tela
data class QuizQuestion(
    val text: String,
    val options: List<String?>?,
    val category: String?,
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
                val cleanQuestions = questionsFromApi?.map { it.toQuizQuestion() }
                val cleanedQuestions = questionsFromApi?.map { q ->
                    q.copy(
                        question = Jsoup.parse(q.question).text(),
                        correctAnswer = Jsoup.parse(q.correctAnswer).text(),
                        incorrectAnswers = q.incorrectAnswers?.map { Jsoup.parse(it).text() }
                    )
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        questions = cleanQuestions ?: emptyList(),
                        currentQuestionIndex = 0,
                        score = 0,
                        isQuizFinished = false
                    )
                }
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Falha ao buscar perguntas da API. Erro: ", e)
                _uiState.update { it.copy(isLoading = false, error = "Falha ao carregar perguntas.") }
            }
        }
    }
    private fun mapQuestions(apiQuestions: List<com.example.quizandroid.data.remote.dto.QuestionResponse>): List<QuizQuestion> {

        // Usamos .mapNotNull para pular automaticamente qualquer pergunta que falhe
        return apiQuestions.mapNotNull { apiQuestion ->

            // --- 1. VALIDAÇÃO ---
            // Criamos variáveis locais NÃO-NULAS.
            // Se qualquer uma falhar (for nula), o 'return@mapNotNull null'
            // pula esta pergunta e vai para a próxima.
            val category = apiQuestion.category ?: return@mapNotNull null
            val text = apiQuestion.question ?: return@mapNotNull null
            val correctAnswer = apiQuestion.correctAnswer ?: return@mapNotNull null

            // 👇 AQUI ESTÁ A CHAVE DO SEU ERRO ATUAL
            // Esta variável 'incorrectAnswers' agora é LOCAL e NÃO-NULA
            val incorrectAnswers = apiQuestion.incorrectAnswers ?: return@mapNotNull null


            // --- 2. LIMPEZA ---
            // Como 'category', 'text', 'correctAnswer' e 'incorrectAnswers'
            // são NÃO-NULAS, podemos usá-las com segurança.

            val cleanCategory = Html.fromHtml(category, Html.FROM_HTML_MODE_LEGACY).toString()
            val cleanText = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
            val cleanCorrect = Html.fromHtml(correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()

            // 👇 Usamos a 'incorrectAnswers' local (NÃO-NULA), por isso .map é seguro
            val cleanIncorrect = incorrectAnswers.map {
                Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString()
            }


            // --- 3. EMBARALHAR ---
            // 👇 Usamos 'cleanIncorrect' (NÃO-NULA), por isso o '+' é seguro
            val options = (cleanIncorrect + cleanCorrect).shuffled()


            // --- 4. RETORNO ---
            QuizQuestion(
                category = cleanCategory,
                text = cleanText,
                options = options,
                correctAnswer = cleanCorrect
            )
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

    // --- CORREÇÃO DE NULOS ---
    // Usamos o '?: ""' (Elvis Operator) para garantir que NUNCA passaremos 'null' para o Jsoup.

    val cleanCorrect = Jsoup.parse(this.correctAnswer ?: "").text()
    val cleanIncorrects = this.incorrectAnswers?.map { Jsoup.parse(it ?: "").text() }

    val options = (cleanIncorrects?.plus(cleanCorrect))?.shuffled()

    return QuizQuestion(
        // O nome do parâmetro na data class é 'text'
        text = Jsoup.parse(this.question ?: "").text(),

        // O nome do parâmetro é 'options'
        options = options,

        // O nome do parâmetro é 'category'
        category = Jsoup.parse(this.category ?: "").text(),

        // O nome do parâmetro é 'correctAnswer'
        correctAnswer = cleanCorrect
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