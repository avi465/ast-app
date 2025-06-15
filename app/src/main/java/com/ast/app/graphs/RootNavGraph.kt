package com.ast.app.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ast.app.presentation.EntryScreen
import com.ast.app.presentation.application.MainScreen

@Composable
fun RootNavigationGraph() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        route = Graph.ROOT,
        startDestination = Screen.ENTRY
    ) {
        composable(route = Screen.ENTRY){
            EntryScreen(rootNavController = rootNavController)
        }
        authNavGraph(rootNavController = rootNavController)
        composable(route = Graph.MAIN_SCREEN_PAGE) {
            MainScreen(rootNavController = rootNavController)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val MAIN_SCREEN_PAGE = "main_screen_graph"
    const val MY_COURSE_DETAILS = "my_course_details_graph"
    const val COURSE_DETAILS = "course_details_graph"
    const val LIVE_CLASS = "live_class_graph"
    const val CART = "cart_graph"
}

object Screen {
    const val ENTRY = "entry_screen"
}