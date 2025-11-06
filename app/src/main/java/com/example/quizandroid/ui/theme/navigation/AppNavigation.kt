package com.example.quizandroid.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizandroid.ui.theme.screens.CategoryScreen
import com.example.quizandroid.ui.theme.screens.StartScreen
import com.example.quizandroid.ui.theme.screens.TelaLogin
import com.example.quizandroid.ui.theme.screens.TelaPerfil
import com.example.quizandroid.ui.theme.screens.TelaEditarPerfil // <-- IMPORTAR A TELA EDITAR PERFIL

// Rotas do App
object AppRoutes {
    const val LOGIN = "login"
    const val START = "start"
    const val CATEGORY = "category"
    const val PROFILE = "profile"
    const val EDITAR_PERFIL = "editarPerfil" // <-- ROTA ADICIONADA
    // Adicione mais rotas aqui (ex: "quiz", "score")
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN // Começa na tela de Login
    ) {
        // Rota para TelaLogin
        composable(AppRoutes.LOGIN) {
            TelaLogin(
                onLoginSucesso = {
                    // Aqui você substitui pelos dados do usuário logado
                    // Agora o ViewModel cuida disso usando o Room, então não precisa mais passar nome e email aqui

                    // Passa os dados para a tela de start
                    navController.navigate(AppRoutes.START) {
                        // Limpa a pilha e garante que o login não vai ser acessado após login bem-sucedido
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Rota para StartScreen
        composable(AppRoutes.START) {
            StartScreen(navController = navController)
        }

        // Rota para CategoryScreen
        composable(AppRoutes.CATEGORY) {
            CategoryScreen(navController = navController)
        }

        // Rota para TelaPerfil
        composable(AppRoutes.PROFILE) {
            // Agora os dados do usuário são carregados via ViewModel + Room
            // (não é mais necessário passar nome e email manualmente)
            TelaPerfil(navController = navController)
        }

        // Rota para TelaEditarPerfil
        composable(AppRoutes.EDITAR_PERFIL) {
            // Tela que permite editar os dados do usuário logado
            TelaEditarPerfil(navController = navController)
        }

        /*
        // Exemplo de como você fará a tela de Quiz (igual ao do professor)
        composable("quiz/{categoria}") { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria")
            // ... chamar sua TelaQuiz(categoria = categoria)
        }
        */
    }
}
