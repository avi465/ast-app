package com.ast.app.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ast.app.R
import com.ast.app.graphs.AuthScreen

@Composable
fun SplashScreen(
    rootNavController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
            ) {
                Image(
//                    imageVector = ImageVector.vectorResource(R.drawable.auth_splash),
                    painter = painterResource(id = R.drawable._6),
                    contentDescription = null, contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_l)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                // continue with phone button
                OutlinedButton(
                    onClick = {
                        rootNavController.navigate(AuthScreen.PhoneLogin.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.PhoneIphone,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
//                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Continue with Phone", style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_s)))

                // login with email button
                OutlinedButton(
                    onClick = {
                        rootNavController.navigate(AuthScreen.EmailLogin.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Log in with Email", style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_s)))

                // continue with google button
                OutlinedButton(
                    onClick = {
                        // navController.navigate(AuthScreen.GoogleLogin.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Continue with Google", style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_s)))

                // don't have an account button
                TextButton(
                    onClick = {
                        rootNavController.navigate(AuthScreen.Signup.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val loginText = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                            append("Don't have an account? ")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(" Signup")
                        }
                    }
                    Text(
                        text = loginText, style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_l)))

                // privacy policies text component
//                 PrivacyPolicy()
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        rootNavController = rememberNavController()
    )
}
