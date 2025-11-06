package com.example.quizandroid.ui.theme.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaEditarPerfil(
    nomeInicial: String = "",
    emailInicial: String = "",
    senhaInicial: String = "",
    onSalvarClick: (String, String, String) -> Unit = { _, _, _ -> }
) {
    // Estados dos campos
    var nome by remember { mutableStateOf(nomeInicial) }
    var email by remember { mutableStateOf(emailInicial) }
    var senha by remember { mutableStateOf(senhaInicial) }

    // Cor de fundo igual às outras telas
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

            // Título
            Text(
                text = "Editar Perfil",
                fontSize = 28.sp,
                color = corTitulo,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo Nome
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Campo Senha
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // Botão Salvar Alterações
            Button(
                onClick = { onSalvarClick(nome, email, senha) },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaEditarPerfilPreview() {
    MaterialTheme {
        TelaEditarPerfil(
            nomeInicial = "aaa",
            emailInicial = "aaaaaa@email.com"
        )
    }
}
