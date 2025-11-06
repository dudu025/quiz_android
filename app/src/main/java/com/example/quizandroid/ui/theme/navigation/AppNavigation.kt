package com.example.quizandroid.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizandroid.ui.theme.screens.CategoryScreen
import com.example.quizandroid.ui.theme.screens.StartScreen
import com.example.quizandroid.ui.theme.screens.TelaLogin
import com.example.quizandroid.ui.theme.screens.TelaPerfil

// Rotas do App
object AppRoutes {
    const val LOGIN = "login"
    const val START = "start"
    const val CATEGORY = "category"
    const val PROFILE = "profile"
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
            TelaPerfil(navController = navController)
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
