package com.ast.app.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ast.app.navigation.TopLevelDestination
import com.ast.app.presentation.application.home.AskDoubtScreen
import com.ast.app.presentation.application.home.HomeScreen
import com.ast.app.presentation.application.home.LiveClassScreen
import com.ast.app.presentation.application.home.MyClassScreen
import com.ast.app.presentation.common.EmptyScreen

@Composable
fun MainNavGraph(modifier: Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.MAIN_SCREEN_PAGE,
        startDestination = TopLevelDestination.Home.route
    ) {
        composable(route = TopLevelDestination.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = TopLevelDestination.LiveClass.route) {
            LiveClassScreen(
                navController = navController
            )
        }
        composable(route = TopLevelDestination.MyClass.route) {
            MyClassScreen(
                navController = navController
            )
        }
        composable(route = TopLevelDestination.AskDoubt.route) {
            AskDoubtScreen(
                navController = navController
            )
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.BTM_DETAIL_PAGE.route
    ) {
        composable(route = DetailsScreen.BTM_DETAIL_PAGE.route) {
//            ScreenContent(name = "Detail Page") {
//                navController.navigate(DetailsScreen.BTM_SUB_DETAILS_PAGE.route)
//            }
            EmptyScreen()
        }
        composable(route = DetailsScreen.BTM_SUB_DETAILS_PAGE.route) {
//            ScreenContent(name = "Sub Detail Page") {}
            EmptyScreen()
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object BTM_DETAIL_PAGE : DetailsScreen(route = "DETAIL_PAGE_")
    object BTM_SUB_DETAILS_PAGE : DetailsScreen(route = "DETAIL_PAGE_SUB")
}