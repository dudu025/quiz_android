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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quizandroid.ui.theme.game.QuizScreen
import com.example.quizandroid.ui.theme.game.TelaFinalDaPartida
import com.example.quizandroid.ui.theme.navigation.AppRoutes
import com.example.quizandroid.ui.theme.screens.* // Importe TODAS as suas telas

// 1. Definição das abas da BottomNavBar
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
fun MainScreen() {
    // 2. Este é o NavController INTERNO (para as abas)
    val navController = rememberNavController()

    Scaffold(
        // 3. A Barra de Navegação Inferior
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
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
        // 4. A lambda de CONTEÚDO do Scaffold
    ) { innerPadding ->

        // 5. O "Navegador Interno" com as telas do app
        NavHost(
            navController = navController,
            startDestination = AppRoutes.START, // A aba inicial
            modifier = Modifier.padding(innerPadding) // Aplica o padding
        ) {
            // Rotas das Abas
            composable(AppRoutes.START) { StartScreen(navController = navController) }
            composable(AppRoutes.HISTORY) { TelaHistorico(onVoltarClick = { navController.popBackStack() }) }
            composable(AppRoutes.PROFILE) { TelaPerfil(navController = navController) }

            // Rotas "internas" (fora das abas)
            composable(AppRoutes.CATEGORY) { CategoryScreen(navController = navController) }
            composable(AppRoutes.EDITAR_PERFIL) { TelaEditarPerfil(navController = navController) }


            composable(AppRoutes.QUIZ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 0
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Quiz"
                QuizScreen(
                    categoryId = categoryId,
                    categoryName = categoryName,
                    navController = navController
                ) }
            composable(AppRoutes.FINAL) { backStackEntry ->
                val score = backStackEntry.arguments?.getString("score") ?: "0/0"
                TelaFinalDaPartida( //
                    pontuacao = score,
                    onVoltarInicioClick = {
                        // Volta para a tela inicial e limpa a pilha
                        navController.popBackStack(AppRoutes.START, false)
                    }
                )
            }
        }
    }
}