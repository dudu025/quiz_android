package com.example.quizandroid.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.R
import com.example.quizandroid.ui.theme.navigation.AppRoutes
import com.example.quizandroid.viewmodel.ProfileViewModel
import com.example.quizandroid.viewmodel.ProfileViewModelFactory

@Composable
fun TelaPerfil(navController: NavHostController) {
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(application.quizRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    val backgroundRoxo = Color(0xFFD1C4E9)

    // ✅ NÃO precisa mais chamar carregarUsuarioLogado() manualmente
    // O ViewModel já faz isso no init {}

    LaunchedEffect(uiState.logoutSucesso) {
        if (uiState.logoutSucesso) {
            navController.navigate(AppRoutes.LOGIN) {
                popUpTo(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Meu Perfil",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A148C),
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.fotoperfil),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.9f))
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = uiState.nome,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = Color(0xFF4A148C)
        )

        Text(
            text = uiState.email,
            fontSize = 16.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController.navigate(AppRoutes.EDITAR_PERFIL) },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Editar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.deslogarUsuario() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Sair", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.deletarUsuarioLogado() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Excluir", color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text("Voltar", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
