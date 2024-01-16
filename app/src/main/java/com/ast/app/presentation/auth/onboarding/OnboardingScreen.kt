package com.ast.app.presentation.auth.onboarding

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ast.app.R
import com.ast.app.presentation.auth.EmailLoginScreen
import com.ast.app.presentation.auth.PasswordResetScreen
import com.ast.app.presentation.auth.PhoneLoginScreen
import com.ast.app.presentation.auth.SignupScreen
import com.ast.app.presentation.auth.SplashScreen
import com.ast.app.presentation.auth.VerifyOtpScreen
import com.ast.app.presentation.auth.VerifyPasswordResetScreen

enum class OnboardingScreen(
    @StringRes val title: Int
) {
    Start(R.string.start),
    PhoneLogin(R.string.phone_login),
    VerifyOtp(R.string.verify_otp),
    EmailLogin(R.string.email_login),
    Signup(R.string.signup),
    PasswordReset(R.string.forgot_password),
    VerifyPasswordReset(R.string.verify_password_reset)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AstAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
//    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    MediumTopAppBar(
        title = {
            Text(
                text = stringResource(currentScreenTitle),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            }
        },
//        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboard() {
    //create NavController
    val navController = rememberNavController()
    //Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    //Get the name of current screen
    val currentScreen = OnboardingScreen.valueOf(
        backStackEntry?.destination?.route ?: OnboardingScreen.Start.name
    )

    /**
     * scrollBehavior is used to get the instance of different
     * scroll behaviors
     */
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        //for showing snackbar
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AstAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
//                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = OnboardingScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = OnboardingScreen.Start.name) {
                SplashScreen(onLoginButtonClicked = {
                    navController.navigate(OnboardingScreen.PhoneLogin.name)
                }, onGetStartedButtonClicked = {
                    navController.navigate(OnboardingScreen.Signup.name)
                })
            }
            composable(route = OnboardingScreen.PhoneLogin.name) {
                PhoneLoginScreen(
                    onSendOtpButtonClicked = {
                        navController.navigate(OnboardingScreen.VerifyOtp.name)
                    }, onLoginWithEmailButtonClicked = {
                        navController.navigate(OnboardingScreen.EmailLogin.name)
                    })
            }

            composable(route = OnboardingScreen.EmailLogin.name) {
                EmailLoginScreen(onForgotPasswordButtonClicked = {
                    navController.navigate(OnboardingScreen.PasswordReset.name)
                })
            }

            composable(route = OnboardingScreen.Signup.name) {
                SignupScreen()
            }

            composable(route = OnboardingScreen.VerifyOtp.name) {
                VerifyOtpScreen(onResendOtpTextClicked = {
                    // for showing snackbar in onClick for example
//                    coroutineScope.launch {
//                        snackbarHostState.showSnackbar(
//                            "OTP Sent", "Ok"
//                        )
//                    }
                })
            }

            composable(route = OnboardingScreen.PasswordReset.name) {
                PasswordResetScreen(onPasswordResetSendButtonClicked = {
                    navController.navigate(OnboardingScreen.VerifyPasswordReset.name)
                })
            }

            composable(route = OnboardingScreen.VerifyPasswordReset.name) {
                VerifyPasswordResetScreen()
            }
        }
    }
}