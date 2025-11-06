package com.example.quizandroid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaPerfil(
    nome: String = "laaaaa",
    email: String = "laaaa@email.com",
    onEditarClick: () -> Unit = {},
    onSairClick: () -> Unit = {},
    onExcluirClick: () -> Unit = {}
) {
    val backgroundRoxo = Color(0xFFD1C4E9) // Mesmo roxo da TelaLogin

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Título
        Text(
            text = "Quiz",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A148C), // Roxo mais escuro para contraste
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        )

        // Avatar genérico
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Foto de perfil",
            tint = Color.Gray,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nome e e-mail
        Text(
            text = nome,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = Color(0xFF311B92)
        )

        Text(
            text = email,
            fontSize = 16.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botão Editar
        Button(
            onClick = onEditarClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Editar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Sair
        Button(
            onClick = onSairClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Sair")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Excluir (vermelho)
        Button(
            onClick = onExcluirClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Excluir", color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPerfilPreview() {
    MaterialTheme {
        TelaPerfil()
    }
}
