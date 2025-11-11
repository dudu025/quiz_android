package com.example.quizandroid.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.ui.theme.navigation.AppRoutes
import com.example.quizandroid.viewmodel.ProfileViewModel
import com.example.quizandroid.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun TelaEditarPerfil(navController: NavHostController) {
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(application.quizRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    // Atualiza campos quando o estado muda
    LaunchedEffect(uiState) {
        nome = uiState.nome
        email = uiState.email
    }

    val scope = rememberCoroutineScope()
    val backgroundRoxo = Color(0xFFD1C4E9)
    val corTitulo = Color(0xFF4A148C)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Editar Perfil",
                fontSize = 28.sp,
                color = corTitulo,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha (opcional)") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    scope.launch {
                        try {
                            viewModel.SalvarPerfil(nome, email, senha)
                            Toast.makeText(context, "Perfil atualizado!", Toast.LENGTH_SHORT).show()
                            // ✅ Volta pra tela de perfil corretamente
                            navController.navigate(AppRoutes.PROFILE) {
                                popUpTo(AppRoutes.EDITAR_PERFIL) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Erro ao salvar: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Salvar Alterações")
            }
        }
    }
}
