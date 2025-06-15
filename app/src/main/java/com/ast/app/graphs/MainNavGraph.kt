package com.ast.app.graphs

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ast.app.navigation.TopLevelDestination
import com.ast.app.presentation.application.course.MyCourseDetailsScreen
import com.ast.app.presentation.application.course.MyCourseScreen
import com.ast.app.presentation.application.home.HomeScreen
import com.ast.app.presentation.application.live.LiveClassScreen
import com.ast.app.presentation.application.live.VideoPlayerScreen
import com.ast.app.presentation.application.profile.SettingsScreen
import com.ast.app.presentation.application.shop.ShopScreen
import com.ast.app.presentation.application.shop.cart.CartScreen
import com.ast.app.presentation.application.shop.course.details.CourseScreen

@Composable
fun MainNavGraph(
    modifier: Modifier,
    rootNavController: NavHostController,
    navController: NavHostController
) {
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
                rootNavController = rootNavController,
                navController = navController
            )
        }
        liveClassNavGraph(navController = navController)
        myCourseDetailsNavGraph(navController = navController)
        courseDetailsNavGraph(navController = navController)
        cartNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.myCourseDetailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.MY_COURSE_DETAILS,
        startDestination = MyCourseDetailsScreen.MyCourseDetails.route
    ) {
        composable(route = MyCourseDetailsScreen.MyCourseDetails.route + "/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            MyCourseDetailsScreen(navController = navController, courseId = courseId ?: "")
        }
    }

}

fun NavGraphBuilder.courseDetailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.COURSE_DETAILS,
        startDestination = CourseDetailsScreen.CourseDetails.route
    ) {
        composable(route = CourseDetailsScreen.CourseDetails.route + "/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            CourseScreen(navController = navController, courseId = courseId ?: "")
        }
    }
}

fun NavGraphBuilder.liveClassNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.LIVE_CLASS,
        startDestination = LiveClassScreen.LiveClassPlayer.route
    ) {
        composable(route = LiveClassScreen.LiveClassPlayer.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                VideoPlayerScreen(navController = navController)
            }
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

sealed class MyCourseDetailsScreen(val route: String) {
    object MyCourseDetails : MyCourseDetailsScreen(route = "my_course_details")
}

sealed class CourseDetailsScreen(val route: String) {
    object CourseDetails : CourseDetailsScreen(route = "course_details")
    object PaymentStatus : CourseDetailsScreen(route = "payment_status")
}

sealed class LiveClassScreen(val route: String) {
    object LiveClassPlayer : LiveClassScreen(route = "live_class_player")
}

sealed class CartScreen(val route: String) {
    object Checkout : CartScreen(route = "checkout")
}