package com.ast.app.presentation.application

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ast.app.graphs.MainNavGraph
import com.ast.app.navigation.AstAppTopAppBar
import com.ast.app.navigation.AstBottomNavBar

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = {
            AstAppTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        },
        bottomBar = {
            AstBottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        MainNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}