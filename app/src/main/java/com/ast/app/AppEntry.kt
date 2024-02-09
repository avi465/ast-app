package com.ast.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ast.app.presentation.application.home.AskDoubtScreen
import com.ast.app.presentation.application.home.HomeScreen
import com.ast.app.presentation.application.home.LiveClassScreen
import com.ast.app.presentation.application.home.MyClassScreen
import com.ast.app.presentation.auth.EmailLoginScreen
import com.ast.app.presentation.auth.PasswordResetScreen
import com.ast.app.presentation.auth.PhoneLoginScreen
import com.ast.app.presentation.auth.SignupScreen
import com.ast.app.presentation.auth.SplashScreen
import com.ast.app.presentation.auth.VerifyOtpScreen
import com.ast.app.presentation.auth.VerifyPasswordResetScreen
import com.ast.app.presentation.auth.onboarding.OnboardingScreen

sealed class AstScreen(val route: String) {
    data object Splash : AstScreen("auth")
    data object App : AstScreen("app") {
        data object Home : AstScreen("home")
        data object LiveClass : AstScreen("live_class")
        data object MyClass : AstScreen("my_class")
        data object AskDoubt : AstScreen("ask_doubt")
    }
}

@Composable
fun AppEntry(modifier: Modifier = Modifier) {
    //create NavController
    val navController = rememberNavController()
    AstNavGraph(navController = navController)
}

@Composable
fun AstNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = AstScreen.Splash.route,
        modifier = modifier
    ) {
        // onboarding navigation graph
        navigation(
            route = AstScreen.Splash.route,
            startDestination = OnboardingScreen.Start.name
        ) {
            composable(route = OnboardingScreen.Start.name) {
                SplashScreen(
                    navController = navController,
                    onLoginButtonClicked = {
                        navController.navigate(OnboardingScreen.PhoneLogin.name)
                    }, onGetStartedButtonClicked = {
                        navController.navigate(OnboardingScreen.Signup.name)
                    }
                )
            }
            composable(route = OnboardingScreen.PhoneLogin.name) {
                PhoneLoginScreen(
                    navController = navController,
                    onSendOtpButtonClicked = {
                        navController.navigate(OnboardingScreen.VerifyOtp.name)
                    }, onLoginWithEmailButtonClicked = {
                        navController.navigate(OnboardingScreen.EmailLogin.name)
                    }
                )
            }

            composable(route = OnboardingScreen.EmailLogin.name) {
                EmailLoginScreen(
                    navController = navController,
                    onForgotPasswordButtonClicked = {
                        navController.navigate(OnboardingScreen.PasswordReset.name)
                    }
                )
            }

            composable(route = OnboardingScreen.Signup.name) {
                SignupScreen(
                    navController = navController
                )
            }

            composable(route = OnboardingScreen.VerifyOtp.name) {
                VerifyOtpScreen(
                    navController = navController,
                    onResendOtpTextClicked = {},
                    onVerifyOtpButtonClicked = {
                        navController.navigate(AstScreen.App.route)
                    }
                )
            }

            composable(route = OnboardingScreen.PasswordReset.name) {
                PasswordResetScreen(
                    navController = navController,
                    onPasswordResetSendButtonClicked = {
                        navController.navigate(OnboardingScreen.VerifyPasswordReset.name)
                    }
                )
            }

            composable(route = OnboardingScreen.VerifyPasswordReset.name) {
                VerifyPasswordResetScreen(
                    navController = navController
                )
            }
        }

        // main app navigation graph
        navigation(
            route = AstScreen.App.route,
            startDestination = AstScreen.App.Home.route
        ) {
            composable(route = AstScreen.App.Home.route) {
                HomeScreen()
            }
            composable(route = AstScreen.App.LiveClass.route) {
                LiveClassScreen()
            }

            composable(route = AstScreen.App.MyClass.route) {
                MyClassScreen()
            }

            composable(route = AstScreen.App.AskDoubt.route) {
                AskDoubtScreen()
            }
        }
    }
}