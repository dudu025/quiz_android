package com.example.quizandroid.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.viewmodel.UserUiState // 1. Importar o novo UiState
import com.example.quizandroid.viewmodel.UserViewModel
import com.example.quizandroid.viewmodel.UserViewModelFactory

@Composable
fun TelaLogin(
    onLoginSucesso: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    val backgroundRoxo = Color(0xFFD1C4E9)
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val quizRepository = application.quizRepository
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(quizRepository))

    // 2. Coletar o novo UiState
    val uiState by viewModel.uiState.collectAsState()

    var emailLogin by remember { mutableStateOf("") }
    var senhaLogin by remember { mutableStateOf("") }
    var nomeCadastro by remember { mutableStateOf("") }
    var emailCadastro by remember { mutableStateOf("") }
    var senhaCadastro by remember { mutableStateOf("") }

    // 3. Remover o 'val mensagem' antigo
    // val mensagem by viewModel.mensagem // <-- REMOVIDO

    var mostrarPopup by remember { mutableStateOf(false) }
    var textoPopup by remember { mutableStateOf("") }

    // 4. (NOVO) LaunchedEffect para reagir ao 'loginSucesso'
    LaunchedEffect(uiState.loginSucesso) {
        if (uiState.loginSucesso) {
            Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
            onLoginSucesso() // Chama a navegação
        }
    }

    // 5. (NOVO) LaunchedEffect para reagir à 'mensagem' (para o popup)
    LaunchedEffect(uiState.mensagem) {
        if (uiState.mensagem.isNotBlank()) {
            textoPopup = uiState.mensagem
            mostrarPopup = true
        }
    }

    Column(
        // ... (o Column continua o mesmo)
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundRoxo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ... (O Text "QUIZ" e "Criar Conta" continuam os mesmos) ...
        Text(
            text = "QUIZ",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = Color(0xFF4A148C),
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF4A148C),
            modifier = Modifier.padding(bottom = 16.dp)
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

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (emailLogin.isBlank() || senhaLogin.isBlank()) {
                    textoPopup = "Preencha os campos necessários"
                    mostrarPopup = true
                } else {
                    viewModel.login(emailLogin, senhaLogin)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { onNavigateToRegister() }) { // O clique é direto aqui
            Text(
                text = "Não tem uma conta? Cadastre-se",
                style = TextStyle(
                    color = Color(0xFF4A148C),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            )
        }

    }

    // 🔹 Popup (AlertDialog)
    // (Esta lógica não precisa de mudança)
    if (mostrarPopup) {
        AlertDialog(
            onDismissRequest = {
                mostrarPopup = false
                // Limpa a mensagem no ViewModel se o popup for fechado
                // (Esta parte é bônus, mas é boa prática)
                // viewModel.clearMessage()
            },
            confirmButton = {
                TextButton(onClick = {
                    mostrarPopup = false
                    // viewModel.clearMessage()
                }) {
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