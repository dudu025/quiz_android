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
fun StartScreen(
    navController: NavHostController
) {
    // corzinha)
    val corFundo = Color(0xFFD1C4E9)
    val corBotaoPrincipal = Color(0xFF9575CD)
    val corBotaoSecundario = Color(0xFFB39DDB)
    val corTextoBotao = Color.White
    val corTextoTitulo = Color(0xFF311B92)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // ... (Spacers e Texto "QUIZ"...)
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "QUIZ",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = corTextoTitulo
        )
        Spacer(modifier = Modifier.weight(1f))

        // botão (Principal)
        Button(
            onClick = {
                navController.navigate(AppRoutes.CATEGORY)
            },

            modifier = Modifier
                .width(220.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = corBotaoPrincipal
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Iniciar",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = corTextoBotao
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // botão (Secundário)
        Button(
            onClick = {
                navController.navigate(AppRoutes.PROFILE)
            },

            modifier = Modifier
                .width(220.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = corBotaoSecundario
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Configurações",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = corTextoBotao
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    QuizAndroidTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            StartScreen(navController = rememberNavController())
        }
    }
}