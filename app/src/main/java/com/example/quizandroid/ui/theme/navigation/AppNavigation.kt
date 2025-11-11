package com.example.quizandroid.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizandroid.ui.theme.screens.CategoryScreen
import com.example.quizandroid.ui.theme.screens.StartScreen
import com.example.quizandroid.ui.theme.screens.TelaLogin
import com.example.quizandroid.ui.theme.screens.TelaPerfil
import com.example.quizandroid.ui.theme.screens.TelaEditarPerfil
import com.example.quizandroid.ui.theme.game.QuizScreen
// 1. ADICIONAR O IMPORT DA TELA FINAL
import com.example.quizandroid.ui.theme.game.TelaFinalDaPartida

// Rotas do App
object AppRoutes {
    const val LOGIN = "login"
    const val START = "start"
    const val CATEGORY = "category"
    const val PROFILE = "profile"
    const val EDITAR_PERFIL = "editarPerfil"
    const val QUIZ = "quiz/{categoryId}/{categoryName}"
    // 2. ADICIONAR A ROTA FINAL
    const val FINAL = "final/{pontuacao}"
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN // Começa na tela de Login
    ) {
        // Rota para TelaLogin (Seu código está correto)
        composable(AppRoutes.LOGIN) {
            TelaLogin(
                onLoginSucesso = {
                    navController.navigate(AppRoutes.START) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Rota para StartScreen (Seu código está correto)
        composable(AppRoutes.START) {
            StartScreen(navController = navController)
        }

        // Rota para CategoryScreen (Seu código está correto)
        composable(AppRoutes.CATEGORY) {
            CategoryScreen(navController = navController)
        }

        // Rota para TelaPerfil (Seu código está correto)
        composable(AppRoutes.PROFILE) {
            TelaPerfil(navController = navController)
        }

        // Rota para TelaEditarPerfil (Seu código está correto)
        composable(AppRoutes.EDITAR_PERFIL) {
            TelaEditarPerfil(navController = navController)
        }

        // --- 3. BLOCO QUE FALTAVA (QUIZ) ---
        composable(AppRoutes.QUIZ) { backStackEntry ->
            // Extrai os argumentos da rota
            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 0
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Quiz"

            QuizScreen(
                categoryId = categoryId,
                categoryName = categoryName,
                navController = navController
            )
        }

        // --- 4. BLOCO QUE FALTAVA (FINAL DA PARTIDA) ---
        composable(AppRoutes.FINAL) { backStackEntry ->
            // Pega a pontuação que foi passada na rota (ex: "8-10")
            // Substitui o hífen "-" por "/" (ex: "8/10")
            val pontuacao = backStackEntry.arguments?.getString("pontuacao")
                ?.replace("-", "/") ?: "0/0"

            // Note que o nome da sua função é "TelaFinalDaPartida"
            TelaFinalDaPartida(
                pontuacao = pontuacao,
                onVoltarInicioClick = {
                    // Ao clicar, limpa tudo e volta para a tela de início
                    navController.navigate(AppRoutes.START) {
                        popUpTo(AppRoutes.START) { inclusive = true }
                    }
                }
            )
        }
    }
}