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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaHistorico(
    historicos: List<Pair<String, String>> = listOf(
        "Esportes" to "10/10",
        "História" to "6/10",
        "Ciências" to "8/10"
    ),
    onVoltarClick: () -> Unit = {}
) {
    val backgroundRoxo = Color(0xFFD1C4E9)
    val corTitulo = Color(0xFF4A148C)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Título principal "Quiz"
        Text(
            text = "Quiz",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = corTitulo,
            modifier = Modifier.padding(top = 24.dp, bottom = 30.dp)
        )

        // Subtítulo "Histórico"
        Text(
            text = "Histórico",
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = corTitulo,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        // Listagem dos históricos
        historicos.forEach { (categoria, pontuacao) ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = categoria,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = corTitulo
                    )
                    Text(
                        text = pontuacao,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Botão "Voltar"
        Button(
            onClick = onVoltarClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
        ) {
            Text("Voltar", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaHistoricoPreview() {
    MaterialTheme {
        TelaHistorico()
    }
}
