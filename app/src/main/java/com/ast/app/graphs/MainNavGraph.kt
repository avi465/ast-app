package com.ast.app.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ast.app.navigation.TopLevelDestination
import com.ast.app.presentation.application.course.MyCourseScreen
import com.ast.app.presentation.application.home.HomeScreen
import com.ast.app.presentation.application.live.LiveClassScreen
import com.ast.app.presentation.application.live.VideoPlayerScreen
import com.ast.app.presentation.application.profile.SettingsScreen
import com.ast.app.presentation.application.shop.ShopScreen
import com.ast.app.presentation.application.shop.cart.CartScreen
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
        composable(route = TopLevelDestination.MyCourse.route) {
            MyCourseScreen(
                navController = navController
            )
        }
        composable(route = TopLevelDestination.Store.route) {
            ShopScreen(
                navController = navController
            )
        }
        composable(route = TopLevelDestination.Settings.route) {
            SettingsScreen(
                navController = navController
            )
        }
        liveClassNavGraph(navController = navController)
        detailsNavGraph(navController = navController)
        cartNavGraph(navController = navController)
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

fun NavGraphBuilder.liveClassNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.LIVE_CLASS,
        startDestination = LiveClassScreen.LiveClassPlayer.route
    ) {
        composable(route = LiveClassScreen.LiveClassPlayer.route) {
            VideoPlayerScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.cartNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.CART,
        startDestination = CartScreen.Checkout.route
    ) {
        composable(route = CartScreen.Checkout.route) {
            CartScreen(navController = navController)
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object BTM_DETAIL_PAGE : DetailsScreen(route = "DETAIL_PAGE_")
    object BTM_SUB_DETAILS_PAGE : DetailsScreen(route = "DETAIL_PAGE_SUB")
}

sealed class LiveClassScreen(val route: String) {
    object LiveClassPlayer : LiveClassScreen(route = "live_class_player")
}

sealed class CartScreen(val route: String) {
    object Checkout : CartScreen(route = "checkout")
}