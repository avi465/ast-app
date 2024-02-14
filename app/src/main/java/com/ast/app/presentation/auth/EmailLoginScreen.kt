package com.ast.app.presentation.auth

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
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ast.app.R
import com.ast.app.graphs.AuthScreen
import com.ast.app.navigation.OnBoardTopAppBar
import com.ast.app.presentation.common.AuthScreenButton
import com.ast.app.presentation.common.ErrorScreen
import com.ast.app.presentation.state.UiState

@Composable
fun EmailLoginScreen(
    emailLoginViewModel: EmailLoginViewModel = viewModel(),
    navController: NavController,
) {
    val uiState by emailLoginViewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Error -> {
            Scaffold {
                ErrorScreen(
                    modifier = Modifier.padding(it),
                    navController = navController
                )
            }
        }

        UiState.Initial -> {
            EmailLoginScreenInitial(
                isLoading = false,
                navController = navController,
                emailLoginViewModel = emailLoginViewModel
            )
        }

        UiState.Loading -> {
            EmailLoginScreenInitial(
                isLoading = true,
                navController = navController,
                emailLoginViewModel = emailLoginViewModel
            )
        }

        is UiState.Success -> {
            return
        }
    }
}

@Composable
fun EmailLoginScreenInitial(
    isLoading: Boolean,
    navController: NavController,
    emailLoginViewModel: EmailLoginViewModel
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OnBoardTopAppBar(
                currentScreenTitle = AuthScreen.EmailLogin.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_l)),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    label = {
                        Text(
                            text = "Enter your e-mail",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    label = {
                        Text(
                            text = "Enter your password",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Password, contentDescription = null)
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Outlined.Key, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = {
                        navController.navigate(AuthScreen.PasswordReset.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.button_height))
                ) {
                    Text(text = "Forgot your password?")
                }

                Spacer(modifier = Modifier.weight(1f))

                AuthScreenButton(
                    text = "Log in",
                    onClick = {
                        emailLoginViewModel.onLoginButtonClicked(email, password, navController)
                    },
                    isLoading = isLoading
                )
            }
        }
    }
}