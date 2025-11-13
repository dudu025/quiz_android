package com.example.quizandroid.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // 1. Importar LazyColumn
import androidx.compose.foundation.lazy.items // 2. Importar items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons // 3. Importar Icons
import androidx.compose.material.icons.filled.Delete // 4. Importar Ícone de Lixeira
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.data.local.entities.Score
import com.example.quizandroid.viewmodel.RankingViewModel
import com.example.quizandroid.viewmodel.RankingViewModelFactory

@Composable
fun TelaHistorico(
    // 5. Remova o parâmetro de dados falsos
    onVoltarClick: () -> Unit = {}
) {
    // 6. Configurar o ViewModel
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: RankingViewModel = viewModel(
        factory = RankingViewModelFactory(application.quizRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    val backgroundRoxo = Color(0xFFD1C4E9)
    val corTitulo = Color(0xFF4A148C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // ... (Os Títulos "Quiz" e "Histórico" continuam iguais) ...
        Text(
            text = "Quiz",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = corTitulo,
            modifier = Modifier.padding(top = 24.dp, bottom = 30.dp)
        )
        Text(
            text = "Histórico",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = corTitulo,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        // 7. Substituir forEach por LazyColumn (Requisito 12)
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.scores.isEmpty()) {
            Text(text = "Nenhum histórico encontrado.", color = corTitulo)
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f) // Adiciona peso para a lista ser rolável
            ) {
                items(uiState.scores) { score ->
                    ScoreCard(
                        score = score,
                        isAdmin = uiState.isAdmin,
                        onDeleteClick = {
                            viewModel.deleteScore(score)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botão "Voltar"
        Button(
            onClick = onVoltarClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
        ) {
            Text("Voltar", fontSize = 18.sp)
        }
    }
}

// 8. Card de Score separado (para Lógica de Admin)
@Composable
fun ScoreCard(
    score: Score,
    isAdmin: Boolean,
    onDeleteClick: () -> Unit
) {
    val corTitulo = Color(0xFF4A148C)

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Alinha o conteúdo
        ) {
            Column(
                horizontalAlignment = Alignment.Start // Alinha texto à esquerda
            ) {
                Text(
                    text = "Pontuação: ${score.pontos}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = corTitulo
                )
                Text(
                    text = "Em: ${score.data}", // Usa dados reais
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Jogador: ${score.userEmail}", // Usa dados reais
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // 9. Lógica de Admin (Requisito 13)
            if (isAdmin) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar Score",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaHistoricoPreview() {
    MaterialTheme {
        TelaHistorico()
    }
}