package com.example.quizandroid.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.ui.theme.QuizAndroidTheme
import com.example.quizandroid.viewmodel.ProfileViewModel
import com.example.quizandroid.viewmodel.ProfileViewModelFactory
import com.example.quizandroid.viewmodel.QuestionViewModel
import com.example.quizandroid.viewmodel.QuestionViewModelFactory

@Composable
fun QuizScreen(
    navController: NavHostController // navhost igual do professor
) {
    // corzinha dnv
    val corFundo = Color(0xFFD1C4E9)
    val corBotaoCategoria = Color(0xFFB39DDB)
    val corTextoTitulo = Color(0xFF311B92)
    val corTextoBotao = Color.White
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: QuestionViewModel = viewModel(
        factory = QuestionViewModelFactory(application.quizRepository)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // texto do Título
        Text(
            text = "Aqui vai a pergunta?",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold,
            color = corTextoTitulo
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(respostas) { categoryName ->
                Button(
                    onClick = {
                        // Aqui tem que funcionar a lógica
                        // de checar se a resposta escolhida é a correta
                        // e de enviar a resposta pro "circuito" que lida
                        // com a pontuação.
                    },

                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = corBotaoCategoria
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = categoryName,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 16.sp,
                        color = corTextoBotao,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizAndroidTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            QuizScreen(navController = rememberNavController())
        }
    }
}