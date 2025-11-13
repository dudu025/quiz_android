package com.example.quizandroid.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.quizandroid.Category
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.ui.theme.QuizAndroidTheme
import com.example.quizandroid.ui.theme.navigation.AppRoutes // Importar AppRoutes
import com.example.quizandroid.viewmodel.ProfileViewModel
import com.example.quizandroid.viewmodel.ProfileViewModelFactory




@Composable
fun CategoryScreen(
    navController: NavHostController
) {
    val categories = listOf(
        Category("Geografia", 22),
        Category("Humor", 20), // (Não há categoria "Humor", talvez "Mythology" (20) ou "Celebrities" (26)?)
        Category("Filmes", 11),
        Category("História", 23),
        Category("Ciência", 17),
        Category("Esportes", 21)
    )
    // corzinha dnv
    val corFundo = Color(0xFFD1C4E9)
    val corBotaoCategoria = Color(0xFFB39DDB)
    val corTextoTitulo = Color(0xFF311B92)
    val corTextoBotao = Color.White

    // Pega as chaves do mapa para os botões
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(application.quizRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

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
            items(categories) { category ->
                Button(
                    onClick = {
                        // --- 2. LÓGICA DE NAVEGAÇÃO ---
                       navController.navigate("quiz/${category.id}")
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
                        text = category.name,
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