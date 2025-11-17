package com.example.quizandroid.ui.theme.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quizandroid.ui.theme.game.QuizScreen
import com.example.quizandroid.ui.theme.game.TelaFinalDaPartida // Importe a tela de Fim de Jogo
import com.example.quizandroid.ui.theme.navigation.AppRoutes // Importe suas Rotas

// Definição das abas da BottomNavBar
sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Start : BottomBarScreen(AppRoutes.START, "Início", Icons.Default.Home)
    object History : BottomBarScreen(AppRoutes.HISTORY, "Histórico", Icons.Default.List)
    object Profile : BottomBarScreen(AppRoutes.PROFILE, "Perfil", Icons.Default.AccountCircle)
}

@Composable
fun MainScreen(
    mainNavController: NavHostController
) {
    // Este é o NavController INTERNO (para as abas)
    val innerNavController = rememberNavController()

    Scaffold(
        // A Barra de Navegação Inferior
        bottomBar = {
            val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            NavigationBar {
                val items = listOf(
                    BottomBarScreen.Start,
                    BottomBarScreen.History,
                    BottomBarScreen.Profile
                )
                items.forEach { screen ->
                    NavigationBarItem(
                        label = { Text(screen.title) },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            innerNavController.navigate(screen.route) {
                                popUpTo(innerNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
        // A lambda de CONTEÚDO do Scaffold
    ) { innerPadding ->

        // O "Navegador Interno" com as telas do app
        NavHost(
            navController = innerNavController,
            startDestination = AppRoutes.START,
            modifier = Modifier.padding(innerPadding) // Aplica o padding
        ) {
            // Rotas das Abas
            composable(AppRoutes.START) { StartScreen(navController = innerNavController) }
            composable(AppRoutes.HISTORY) { TelaHistorico(onVoltarClick = { innerNavController.popBackStack() }) }
            composable(AppRoutes.PROFILE) { TelaPerfil(innerNavController = innerNavController, mainNavController =  mainNavController) }

            // Rotas "internas" (fora das abas)
            composable(AppRoutes.CATEGORY) { CategoryScreen(navController = innerNavController) }
            composable(AppRoutes.EDITAR_PERFIL) { TelaEditarPerfil(navController = innerNavController) }

            // --- ESTAS SÃO AS ROTAS QUE ESTAVAM FALTANDO ---

            // Rota para a Tela de Jogo
            composable(AppRoutes.QUIZ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 0

                QuizScreen(
                    categoryId = categoryId,
                    navController = innerNavController
                )
            }

            // Rota para a Tela de Resultado
            composable(AppRoutes.FINAL) { backStackEntry ->
                val score = backStackEntry.arguments?.getString("score") ?: "0/0"

                TelaFinalDaPartida(
                    pontuacao = score,
                    onVoltarInicioClick = {
                        innerNavController.popBackStack(AppRoutes.START, false)
                    }
                )
            }
        }
    }
}