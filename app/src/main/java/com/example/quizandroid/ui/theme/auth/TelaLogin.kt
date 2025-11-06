package com.example.quizandroid.ui.theme.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TelaLogin(
    onCreateAccount: (String, String, String) -> Unit = { _, _, _ -> },
    onLogin: (String, String) -> Unit = { _, _ -> }
) {
    var emailCadastro by remember { mutableStateOf("") }
    var nomeCadastro by remember { mutableStateOf("") }
    var senhaCadastro by remember { mutableStateOf("") }

    var emailLogin by remember { mutableStateOf("") }
    var senhaLogin by remember { mutableStateOf("") }

    // Fundo roxo médio
    val backgroundRoxo = Color(0xFFD1C4E9)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Nome do app
        Text(
            text = "QUIZ",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Criar Conta
        Text(
            text = "Criar Conta",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF4A148C)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = emailCadastro,
            onValueChange = { emailCadastro = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nomeCadastro,
            onValueChange = { nomeCadastro = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = senhaCadastro,
            onValueChange = { senhaCadastro = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onCreateAccount(emailCadastro, nomeCadastro, senhaCadastro) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Conta")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Login
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF4A148C)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = emailLogin,
            onValueChange = { emailLogin = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = senhaLogin,
            onValueChange = { senhaLogin = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLogin(emailLogin, senhaLogin) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTelaLogin() {
    MaterialTheme {
        TelaLogin()
    }
}
