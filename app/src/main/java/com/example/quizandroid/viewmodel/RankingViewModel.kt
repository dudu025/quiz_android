package com.example.quizandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quizandroid.data.local.entities.Score
import com.example.quizandroid.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. O UiState para a tela de Ranking
data class RankingUiState(
    val scores: List<Score> = emptyList(),
    val isAdmin: Boolean = false,
    val isLoading: Boolean = true
)

class RankingViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RankingUiState())
    val uiState: StateFlow<RankingUiState> = _uiState.asStateFlow()

    init {
        loadRankingData()
    }

    private fun loadRankingData() {
        viewModelScope.launch {
            // 1. Verifica se o usuário é Admin (Requisito 13)
            val user = repository.getUserLogado()
            _uiState.update { it.copy(isAdmin = user?.isAdmin == true) }

            // 2. Coleta o Flow de scores (Requisito 6)
            repository.getAllScores()
                .map { scores ->
                    // 3. Aplica a ordenação (Requisito 11)
                    scores.sortedByDescending { it.pontos }
                }
                .collect { sortedScores ->
                    _uiState.update {
                        it.copy(
                            scores = sortedScores,
                            isLoading = false
                        )
                    }
                }
        }
    }

    // 4. Função para deletar um score (Requisito 7)
    fun deleteScore(score: Score) {
        viewModelScope.launch {
            repository.deleteScore(score)
            // A lista irá atualizar sozinha graças ao Flow!
        }
    }
}



