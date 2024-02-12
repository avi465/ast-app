package com.ast.app.graphs

import androidx.annotation.StringRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ast.app.R
import com.ast.app.presentation.auth.EmailLoginScreen
import com.ast.app.presentation.auth.EmailLoginViewModel
import com.ast.app.presentation.auth.PasswordResetScreen
import com.ast.app.presentation.auth.PhoneLoginScreen
import com.ast.app.presentation.auth.SignupScreen
import com.ast.app.presentation.auth.SplashScreen
import com.ast.app.presentation.auth.VerifyOtpScreen
import com.ast.app.presentation.auth.VerifyPasswordResetScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Start.route
    ) {

        composable(route = AuthScreen.Start.route) {
            SplashScreen(
                navController = navController,
                onLoginButtonClicked = {
                    navController.navigate(AuthScreen.PhoneLogin.route)
                }, onGetStartedButtonClicked = {
                    navController.navigate(AuthScreen.Signup.route)
                }
            )
        }
        composable(route = AuthScreen.PhoneLogin.route) {
            PhoneLoginScreen(
                navController = navController,
                onSendOtpButtonClicked = {
                    navController.navigate(AuthScreen.VerifyOtp.route)
                }, onLoginWithEmailButtonClicked = {
                    navController.navigate(AuthScreen.EmailLogin.route)
                }
            )
        }

        composable(route = AuthScreen.EmailLogin.route) {
            EmailLoginScreen(
                navController = navController,
                onForgotPasswordButtonClicked = {
                    navController.navigate(AuthScreen.PasswordReset.route)
                }
            )
        }

        composable(route = AuthScreen.Signup.route) {
            SignupScreen(
                navController = navController
            )
        }

        composable(route = AuthScreen.VerifyOtp.route) {
            VerifyOtpScreen(
                navController = navController,
                onResendOtpTextClicked = {},
                onVerifyOtpButtonClicked = {
                    navController.navigate(Graph.MAIN_SCREEN_PAGE)
                }
            )
        }

        composable(route = AuthScreen.PasswordReset.route) {
            PasswordResetScreen(
                navController = navController,
                onPasswordResetSendButtonClicked = {
                    navController.navigate(AuthScreen.VerifyPasswordReset.route)
                }
            )
        }

        composable(route = AuthScreen.VerifyPasswordReset.route) {
            VerifyPasswordResetScreen(
                navController = navController
            )
        }
    }
}

sealed class AuthScreen(val route: String, @StringRes val title: Int) {
    object Start : AuthScreen(route = "start", title = R.string.start)
    object PhoneLogin : AuthScreen(route = "phone_login", title = R.string.phone_login)
    object VerifyOtp : AuthScreen(route = "verify_otp", title = R.string.verify_otp)
    object EmailLogin : AuthScreen(route = "email_login", title = R.string.email_login)
    object Signup : AuthScreen(route = "signup", title = R.string.signup)
    object PasswordReset : AuthScreen(route = "password_reset", title = R.string.forgot_password)
    object VerifyPasswordReset :
        AuthScreen(route = "verify_password_reset", title = R.string.verify_password_reset)
}