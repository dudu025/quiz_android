package com.example.quizandroid.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizandroid.QuizApplication
import com.example.quizandroid.viewmodel.UserViewModel
import com.example.quizandroid.viewmodel.UserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaRegistro(
    onRegistroSucesso: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val backgroundRoxo = Color(0xFFD1C4E9)
    val context = LocalContext.current
    val application = context.applicationContext as QuizApplication
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(application.quizRepository))

    val uiState by viewModel.uiState.collectAsState()

    var nomeCadastro by remember { mutableStateOf("") }
    var emailCadastro by remember { mutableStateOf("") }
    var senhaCadastro by remember { mutableStateOf("") }

    var mostrarPopup by remember { mutableStateOf(false) }
    var textoPopup by remember { mutableStateOf("") }

    // Efeito para mostrar popups e navegar de volta
    LaunchedEffect(uiState.mensagem) {
        if (uiState.mensagem.isNotBlank()) {
            textoPopup = uiState.mensagem
            mostrarPopup = true

            if (uiState.mensagem == "Conta criada com sucesso!") {
                // Se deu certo, o popup vai fechar e vamos navegar
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Conta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundRoxo
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundRoxo)
                .padding(padding) // Usa o padding da TopBar
                .padding(horizontal = 24.dp), // Padding lateral
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (nomeCadastro.isBlank() || emailCadastro.isBlank() || senhaCadastro.isBlank()) {
                        textoPopup = "Preencha os campos necessários"
                        mostrarPopup = true
                    } else {
                        viewModel.criarUsuario(nomeCadastro, emailCadastro, senhaCadastro)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Criar Conta")
            }

            Spacer(modifier = Modifier.weight(1f)) // Empurra tudo para cima
        }
    }


    // --- Popup (AlertDialog) ---
    if (mostrarPopup) {
        AlertDialog(
            onDismissRequest = {
                mostrarPopup = false
                if(textoPopup == "Conta criada com sucesso!") {
                    onRegistroSucesso() // Navega de volta
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    mostrarPopup = false
                    if(textoPopup == "Conta criada com sucesso!") {
                        onRegistroSucesso() // Navega de volta
                    }
                }) {
                    Text("OK")
                }
            },
            title = { Text("Aviso") },
            text = { Text(textoPopup) }
        )
    }
}

