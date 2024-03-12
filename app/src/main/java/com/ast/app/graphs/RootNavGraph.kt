package com.ast.app.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ast.app.presentation.EntryScreen
import com.ast.app.presentation.application.MainScreen

@Composable
fun RootNavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        route = Graph.ROOT,
//        startDestination = Graph.AUTHENTICATION
        startDestination = Screen.ENTRY
    ) {
        composable(route = Screen.ENTRY){
            EntryScreen(navController = navController)
        }
        authNavGraph(navController = navController)
        composable(route = Graph.MAIN_SCREEN_PAGE) {
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val MAIN_SCREEN_PAGE = "main_screen_graph"
    const val DETAILS = "details_graph"
}

object Screen {
    const val ENTRY = "entry_screen"
}