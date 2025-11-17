package com.example.quizandroid.ui.theme.screens

import android.provider.ContactsContract.Profile
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
import com.example.quizandroid.data.remote.OpenTdbApiService
import com.example.quizandroid.data.repository.QuizRepository
import com.example.quizandroid.ui.theme.navigation.AppRoutes
import com.example.quizandroid.viewmodel.ProfileViewModel
import com.example.quizandroid.viewmodel.ProfileViewModelFactory
import com.example.quizandroid.viewmodel.UserViewModel
import com.example.quizandroid.viewmodel.UserViewModelFactory

@Composable
fun TelaEditarPerfil(navController: NavHostController) {
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(application.quizRepository)
    )
    val uiState by viewModel.uiState.collectAsState()


    var nome by remember(uiState.nome) { mutableStateOf(uiState.nome) }
    var email by remember(uiState.email) { mutableStateOf(uiState.email) }
    var senha by remember { mutableStateOf("") }

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
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    viewModel.SalvarPerfil(nome, email, senha)
                    navController.navigate(AppRoutes.PROFILE)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Salvar Alterações")
            }
        }
    }
}
