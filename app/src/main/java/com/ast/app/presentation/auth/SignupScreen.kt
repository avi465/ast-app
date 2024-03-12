package com.ast.app.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.ast.app.presentation.common.PrivacyPolicy
import com.ast.app.presentation.state.UiState
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    signupViewModel: SignupViewModel = viewModel(),
    navController: NavController
) {
    val uiState by signupViewModel.uiState.collectAsState()

    var name by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var isNameFieldValid by rememberSaveable {
        mutableStateOf(true)
    }
    var isEmailFieldValid by rememberSaveable {
        mutableStateOf(true)
    }
    var isPasswordFieldValid by rememberSaveable {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OnBoardTopAppBar(
                currentScreenTitle = AuthScreen.Signup.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                            text = "Enter your full name*",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    value = name,
                    onValueChange = {
                        name = it
                        isNameFieldValid = name.isNotBlank()
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
                    },
                    trailingIcon = {
                        if (!isNameFieldValid) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = "error"
                            )
                        } else if (name.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    name = ""
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = "cancel",
                                )
                            }
                        }
                    },
                    isError = !isNameFieldValid,
                    supportingText = {
                        if (!isNameFieldValid) {
                            Text(
                                text = "Name can't be blank",
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Text(text = "*required")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Enter e-mail address*",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailFieldValid = email.isNotBlank()
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    trailingIcon = {
                        if (!isEmailFieldValid) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = "error"
                            )
                        } else if (email.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    email = ""
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = "cancel",
                                )
                            }
                        }
                    },
                    isError = !isEmailFieldValid,
                    supportingText = {
                        if (!isEmailFieldValid) {
                            Text(
                                text = "Email can't be blank",
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Text(text = "*required")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    label = {
                        Text(
                            text = "Enter strong password*",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    value = password,
                    onValueChange = {
                        password = it
                        isPasswordFieldValid = password.isNotBlank()
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Password, contentDescription = null)
                    },
                    trailingIcon = {
                        if (!isEmailFieldValid) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = "error"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Key,
                                contentDescription = "show/hide password"
                            )
                        }
                    },
                    isError = !isPasswordFieldValid,
                    supportingText = {
                        if (!isPasswordFieldValid) {
                            Text(
                                text = "Password can't be blank",
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Text(text = "*required")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

                when (uiState) {
                    is UiState.Error -> {
                        LaunchedEffect(snackbarHostState) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    (uiState as UiState.Error).error,
                                    actionLabel = "Dismiss",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                        AuthScreenButton(
                            text = "Sign up",
                            onClick = {
                                if (name.isBlank()) {
                                    isNameFieldValid = false
                                }
                                if (email.isBlank()) {
                                    isEmailFieldValid = false
                                }
                                if (password.isBlank()) {
                                    isPasswordFieldValid = false
                                }
                                if (isNameFieldValid && isEmailFieldValid && isPasswordFieldValid) {
                                    signupViewModel.onSignupButtonClicked(
                                        name,
                                        email,
                                        password,
                                        navController
                                    )
                                }
                            },
                            isLoading = false
                        )
                    }

                    UiState.Initial -> {
                        AuthScreenButton(
                            text = "Sign up",
                            onClick = {
                                if (name.isBlank()) {
                                    isNameFieldValid = false
                                }
                                if (email.isBlank()) {
                                    isEmailFieldValid = false
                                }
                                if (password.isBlank()) {
                                    isPasswordFieldValid = false
                                }
                                if (isNameFieldValid && isEmailFieldValid && isPasswordFieldValid) {
                                    signupViewModel.onSignupButtonClicked(
                                        name,
                                        email,
                                        password,
                                        navController
                                    )
                                }
                            },
                            isLoading = false
                        )
                    }

                    UiState.Loading -> {
                        AuthScreenButton(
                            text = "Sign up",
                            onClick = {
                                if (name.isBlank()) {
                                    isNameFieldValid = false
                                }
                                if (email.isBlank()) {
                                    isEmailFieldValid = false
                                }
                                if (password.isBlank()) {
                                    isPasswordFieldValid = false
                                }
                                if (isNameFieldValid && isEmailFieldValid && isPasswordFieldValid) {
                                    signupViewModel.onSignupButtonClicked(
                                        name,
                                        email,
                                        password,
                                        navController
                                    )
                                }
                            },
                            isLoading = true
                        )
                    }

                    is UiState.Success -> {
                    }
                }

                //privacy policy component
                PrivacyPolicy()
            }
        }
    }
}
