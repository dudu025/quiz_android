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
import com.example.quizandroid.ui.theme.screens.MainScreen
import com.example.quizandroid.ui.theme.screens.TelaRegistro

// Rotas do App
object AppRoutes {
    const val LOGIN = "login"
    const val MAIN = "main"
    const val START = "start"
    const val CATEGORY = "category"
    const val HISTORY = "history"
    const val PROFILE = "profile"
    const val REGISTER = "register"
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
                    navController.navigate(AppRoutes.MAIN) {
                        popUpTo(AppRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }
        composable(AppRoutes.REGISTER) {
            TelaRegistro(onRegistroSucesso = {
                navController.popBackStack()
            },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.MAIN) {
            MainScreen(mainNavController = navController)
        }

    }
}

