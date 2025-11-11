package com.example.quizandroid.ui.theme.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // <-- Importe o TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.ui.theme.navigation.AppRoutes
import com.example.quizandroid.viewmodel.QuizUiState
import com.example.quizandroid.viewmodel.QuizViewModel
import com.example.quizandroid.viewmodel.QuizViewModelFactory

@Composable
fun QuizScreen(
    categoryId: Int,
    categoryName: String,
    navController: NavHostController
) {
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: QuizViewModel = viewModel(
        factory = QuizViewModelFactory(application.quizRepository)
    )

    // Coleta o estado do ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Carrega as perguntas (apenas uma vez)
    LaunchedEffect(Unit) {
        viewModel.loadQuestions(categoryId)
    }

    // Navega para a tela final quando o quiz acabar
    LaunchedEffect(uiState.isQuizFinished) {
        if (uiState.isQuizFinished) {
            val score = uiState.finalScoreText // "8-10"
            // TODO: Salvar o score no banco de dados (Room) aqui

            // Navega para a tela final
            navController.navigate(AppRoutes.FINAL.replace("{pontuacao}", score)) {
                popUpTo(AppRoutes.START) // Limpa a pilha de volta para a tela inicial
            }
        }
    }

    // --- Layout da Tela ---
    val corFundo = Color(0xFFD1C4E9)
    val corTitulo = Color(0xFF311B92)

    Surface(modifier = Modifier.fillMaxSize(), color = corFundo) {

        // --- ESTE BLOCO 'WHEN' FOI ATUALIZADO ---
        when {
            // 1. Estado de Carregamento
            uiState.isLoading -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
            // 2. Estado de Erro
            uiState.error != null -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = uiState.error!!, color = Color.Red, fontSize = 18.sp)
                }
            }
            // 3. Estado de Sucesso (Quiz)
            uiState.currentQuestion != null -> {
                QuizContent(
                    categoryName = categoryName,
                    uiState = uiState,
                    onAnswerSelected = { viewModel.selectAnswer(it) },
                    onNextClick = { viewModel.nextQuestion() }
                )
            }
            // 4. (NOVO) Estado de "uepa mas vazio"
            // Isso previne a tela em branco!
            !uiState.isLoading && uiState.questions.isEmpty() -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Nenhuma pergunta encontrada para esta categoria.",
                        color = corTitulo,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// ... (O resto do arquivo QuizContent continua igual)

@Composable
fun QuizContent(
    categoryName: String,
    uiState: QuizUiState,
    onAnswerSelected: (String) -> Unit,
    onNextClick: () -> Unit
) {
    val question = uiState.currentQuestion!!
    val corFundo = Color(0xFFD1C4E9)
    val corTitulo = Color(0xFF311B92)
    val corBotao = Color(0xFFB39DDB)
    val corTextoBotao = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título da Categoria e Progresso
        Text(text = categoryName, style = MaterialTheme.typography.headlineMedium, color = corTitulo)
        Text(text = uiState.progressText, style = MaterialTheme.typography.bodyLarge, color = corTitulo)

        Spacer(modifier = Modifier.height(24.dp))

        // Card da Pergunta
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = question.text,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Opções de Resposta
        question.options.forEach { option ->
            val isSelected = uiState.isAnswerSelected && uiState.selectedAnswer == option

            // Define a cor com base na seleção e correção
            val (corBorda, corFundoBotao) = when {
                !uiState.isAnswerSelected -> Pair(Color.Transparent, corBotao) // Padrão
                isSelected && option == question.correctAnswer -> Pair(Color(0xFF00C853), corBotao.copy(alpha = 0.5f)) // Correta
                isSelected && option != question.correctAnswer -> Pair(Color.Red, corBotao.copy(alpha = 0.5f)) // Errada
                option == question.correctAnswer -> Pair(Color(0xFF00C853), corBotao.copy(alpha = 0.5f)) // Mostra a correta (quando outra é selecionada)
                else -> Pair(Color.Transparent, corBotao.copy(alpha = 0.5f)) // Outras
            }

            Button(
                onClick = { onAnswerSelected(option) },
                enabled = !uiState.isAnswerSelected, // Desativa botões após responder
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .height(50.dp)
                    .border(3.dp, corBorda, RoundedCornerShape(12.dp)), // Borda mais grossa
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = corFundoBotao)
            ) {
                Text(text = option, color = corTextoBotao, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de Próximo
        if (uiState.isAnswerSelected) {
            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(
                    text = if (uiState.currentQuestionIndex == uiState.questions.size - 1) "Finalizar" else "Próxima",
                    fontSize = 18.sp
                )
            }
        }
    }
}