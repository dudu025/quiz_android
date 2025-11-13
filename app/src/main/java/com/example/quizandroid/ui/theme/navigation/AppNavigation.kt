package com.example.quizandroid.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        composable(
            route = "quiz/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->

            // 1. Extrai o argumento (será Int? ou seja, nulo se não for encontrado)
            val categoryId = backStackEntry.arguments?.getInt("categoryId")

            // 2. Verifica se o ID foi recebido corretamente
            if (categoryId != null) {
                // 3. Se SIM, chama a QuizScreen APENAS AQUI
                // Dentro deste 'if', o Kotlin sabe que 'categoryId' é um Int (não-nulo)
                QuizScreen(
                    navController = navController,
                    categoryId = categoryId
                )
            } else {
                // 4. Se NÃO (ex: erro na navegação), apenas volte para a tela anterior
                navController.popBackStack()
            }

        // --- 4. BLOCO QUE FALTAVA (FINAL DA PARTIDA) ---

        }
        composable(
            route = AppRoutes.FINAL,
            arguments = listOf(navArgument("pontuacao") { type = NavType.StringType })
        ) { backStackEntry ->
            // Extrai a pontuação da rota
            val pontuacao = backStackEntry.arguments?.getString("pontuacao") ?: "0/0"

            TelaFinalDaPartida(
                pontuacao = pontuacao,
                onVoltarInicioClick = {
                    // Volta para a tela START, limpando a pilha
                    navController.navigate(AppRoutes.START) {
                        popUpTo(AppRoutes.START) { inclusive = true }
                    }
                }
            )
        }
    }
}

