package com.example.quizandroid.ui.theme.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaFinalPartida(
    pontuacao: String = "10/10",
    onVoltarInicioClick: () -> Unit = {}
) {
    val backgroundRoxo = Color(0xFFD1C4E9)
    val corTitulo = Color(0xFF4A148C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Parte superior: "Quiz" e "Fim de Partida"
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Quiz",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = corTitulo,
                modifier = Modifier.padding(top = 28.dp, bottom = 16.dp)
            )

            Text(
                text = "Fim de Partida",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = corTitulo,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        // Centralizar o restante
        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sua Pontuação",
                fontSize = 32.sp,
                color = Color(0xFF311B92),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom =25.dp)
            )

            Text(
                text = pontuacao,
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF4A148C),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 50.dp)
            )

            Button(
                onClick = onVoltarInicioClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(220.dp)
                    .height(70.dp)
            ) {
                Text("Voltar ao Início", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaFinalPartidaPreview() {
    MaterialTheme {
        TelaFinalPartida(pontuacao = "8/10")
    }
}
