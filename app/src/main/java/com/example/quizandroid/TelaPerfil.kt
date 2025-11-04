package com.example.quizandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun TelaPerfil(navController: NavHostController) {
    // corzinha pra deixar bonito
    val corFundo = Color(0xFFD1C4E9)
    val corTextoTitulo = Color(0xFF311B92)
    val corBotao = Color(0xFFB39DDB)
    val corTextoBotao = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Meu Perfil",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = corTextoTitulo
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Você pode adicionar mais Text() aqui com os dados do usuário
        Text(
            text = "Email: (email.do.usuario@email.com)",
            style = MaterialTheme.typography.bodyLarge,
            color = corTextoTitulo
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Nome: (Nome do Usuário)",
            style = MaterialTheme.typography.bodyLarge,
            color = corTextoTitulo
        )

        Spacer(modifier = Modifier.weight(1f)) // botão para baixo

        // Botão Voltar
        Button(
            onClick = {
                // voltar para a tela anterior
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = corBotao
            )
        ) {
            Text(
                text = "Voltar",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = corTextoBotao
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TelaPerfilPreview() {
    QuizAndroidTheme {
        TelaPerfil(navController = rememberNavController())
    }
}