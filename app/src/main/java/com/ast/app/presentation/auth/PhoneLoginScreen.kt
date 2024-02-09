package com.ast.app.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ast.app.R
import com.ast.app.presentation.auth.onboarding.OnboardingScreen
import com.ast.app.navigation.OnBoardTopAppBar
import com.ast.app.presentation.common.OrWithDivider
import com.ast.app.presentation.common.PrivacyPolicy

@Composable
fun PhoneLoginScreen(
    onSendOtpButtonClicked: () -> Unit,
    onLoginWithEmailButtonClicked: () -> Unit,
    navController: NavController
) {
    var value by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OnBoardTopAppBar(
                currentScreenTitle = OnboardingScreen.PhoneLogin.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        }
    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_l)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_l)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Enter your phone number",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    value = value,
                    onValueChange = { value = it },
                    prefix = {
                        Text(
                            text = "+91",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    leadingIcon = {
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.india_flag_icon),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                //OrWithDivider component
                OrWithDivider()

                OutlinedButton(
                    onClick = onLoginWithEmailButtonClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.button_height))
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Log in with E-mail", style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                OutlinedButton(
                    onClick = onLoginWithEmailButtonClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.button_height))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Log in with Google", style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onSendOtpButtonClicked,
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.button_height))
                ) {
                    Text(text = "Send OTP", style = MaterialTheme.typography.titleMedium)
                }

                //PrivacyPolicy component
                PrivacyPolicy()
            }
        }
    }
}
