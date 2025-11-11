package com.example.quizandroid.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.quizandroid.ui.theme.QuizAndroidTheme
import com.example.quizandroid.ui.theme.navigation.AppRoutes // Importar AppRoutes

// --- 1. MAPEAMENTO DOS IDs DA API (OpenTDB) ---
val categoryMap = mapOf(
    "Geografia" to 22,
    "Filmes" to 11,
    "História" to 23,
    "Ciência" to 17,
    "Esportes" to 21,
    "Humor" to 9 // "Humor" não existe na API, usei "Conhecimentos Gerais" (ID 9)
)

@Composable
fun CategoryScreen(
    navController: NavHostController
) {
    // corzinha dnv
    val corFundo = Color(0xFFD1C4E9)
    val corBotaoCategoria = Color(0xFFB39DDB)
    val corTextoTitulo = Color(0xFF311B92)
    val corTextoBotao = Color.White

    // Pega as chaves do mapa para os botões
    val categories = categoryMap.keys.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // texto do Título
        Text(
            text = "Escolha uma Categoria",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold,
            color = corTextoTitulo
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(categories) { categoryName ->
                Button(
                    onClick = {
                        // --- 2. LÓGICA DE NAVEGAÇÃO ---
                        val categoryId = categoryMap[categoryName] ?: 9 // Pega o ID

                        // Navega para a tela do Quiz
                        navController.navigate(
                            AppRoutes.QUIZ
                                .replace("{categoryId}", categoryId.toString())
                                .replace("{categoryName}", categoryName)
                        )
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
fun CategoryScreenPreview() {
    QuizAndroidTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CategoryScreen(navController = rememberNavController())
        }
    }
}