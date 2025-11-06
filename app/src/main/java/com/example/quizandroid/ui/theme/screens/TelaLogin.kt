<<<<<<<< HEAD:app/src/main/java/com/example/quizandroid/ui/theme/auth/TelaLogin.kt
package com.example.quizandroid.ui.theme.auth
========
package com.example.quizandroid.ui.theme.screens
>>>>>>>> febc67f52c204dba3ea3471834182f55f958af6e:app/src/main/java/com/example/quizandroid/ui/theme/screens/TelaLogin.kt

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizandroid.data.repository.UserRepository
import com.example.quizandroid.viewmodel.UserViewModel
import com.example.quizandroid.viewmodel.UserViewModelFactory

@Composable
fun TelaLogin(
    onLoginSucesso: () -> Unit = {} // chamada quando login for bem-sucedido
) {
    val backgroundRoxo = Color(0xFFD1C4E9)
    val context = LocalContext.current
    val repository = UserRepository(context)
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(repository))

    var emailLogin by remember { mutableStateOf("") }
    var senhaLogin by remember { mutableStateOf("") }
    var nomeCadastro by remember { mutableStateOf("") }
    var emailCadastro by remember { mutableStateOf("") }
    var senhaCadastro by remember { mutableStateOf("") }

    val mensagem by viewModel.mensagem

    var mostrarPopup by remember { mutableStateOf(false) }
    var textoPopup by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "QUIZ",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = Color(0xFF4A148C)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Criar Conta ---
        Text(
            text = "Criar Conta",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF4A148C)
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
            value = emailCadastro,
            onValueChange = { emailCadastro = it },
            label = { Text("Email") },
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

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (nomeCadastro.isBlank() || emailCadastro.isBlank() || senhaCadastro.isBlank()) {
                    textoPopup = "Preencha os campos necessários"
                    mostrarPopup = true
                } else {
                    viewModel.criarUsuario(nomeCadastro, emailCadastro, senhaCadastro)
                    textoPopup = "Conta criada com sucesso!"
                    mostrarPopup = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar Conta")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Login ---
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF4A148C)
        )

        Spacer(modifier = Modifier.height(8.dp))
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

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (emailLogin.isBlank() || senhaLogin.isBlank()) {
                    textoPopup = "Preencha os campos necessários"
                    mostrarPopup = true
                } else {
                    viewModel.login(emailLogin, senhaLogin) {
                        Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                        onLoginSucesso() // 🔹 chama a navegação definida no AppNavigation
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = mensagem, color = Color.Black, textAlign = TextAlign.Center)
    }

    // 🔹 Popup (AlertDialog)
    if (mostrarPopup) {
        AlertDialog(
            onDismissRequest = { mostrarPopup = false },
            confirmButton = {
                TextButton(onClick = { mostrarPopup = false }) {
                    Text("OK")
                }
            },
            title = { Text("Aviso") },
            text = { Text(textoPopup) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaLogin() {
    TelaLogin(onLoginSucesso = {})
}
