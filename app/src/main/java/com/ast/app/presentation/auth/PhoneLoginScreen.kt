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
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.rounded.PhoneIphone
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ast.app.R
import com.ast.app.graphs.AuthScreen
import com.ast.app.navigation.OnBoardTopAppBar
import com.ast.app.presentation.common.AuthScreenButton
import com.ast.app.presentation.state.UiState
import kotlinx.coroutines.launch

var enteredPhoneNumber = ""

@Composable
fun PhoneLoginScreen(
    phoneLoginViewModel: PhoneLoginViewModel = viewModel(),
    rootNavController: NavHostController
) {
    val uiState by phoneLoginViewModel.uiState.collectAsState()

    var phone by rememberSaveable {
        mutableStateOf("")
    }
    var isPhoneFieldValid by rememberSaveable {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OnBoardTopAppBar(
                currentScreenTitle = AuthScreen.PhoneLogin.title,
                canNavigateBack = rootNavController.previousBackStackEntry != null,
                navigateUp = { rootNavController.navigateUp() },
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
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_l)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Enter phone number*",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    value = phone,
                    onValueChange = {
                        phone = it
                        isPhoneFieldValid = phone.isNotBlank()
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.PhoneIphone,
                            contentDescription = "phone number"
                        )
                    },
                    trailingIcon = {
                        if (!isPhoneFieldValid) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = "error"
                            )
                        } else if (phone.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    phone = ""
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = "cancel",
                                )
                            }
                        }
                    },
                    isError = !isPhoneFieldValid,
                    supportingText = {
                        if (!isPhoneFieldValid) {
                            Text(
                                text = "Phone can't be blank",
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
                            text = "Send OTP",
                            onClick = {
                                if (phone.isBlank()) {
                                    isPhoneFieldValid = false
                                }
                                if (isPhoneFieldValid) {
                                    enteredPhoneNumber = phone
                                    phoneLoginViewModel.onGetPhoneOtpButtonClicked(
                                        phone,
                                        rootNavController
                                    )
                                }
                            },
                            isLoading = false
                        )
                    }

                    UiState.Initial -> {
                        AuthScreenButton(
                            text = "Get OTP",
                            onClick = {
                                if (phone.isBlank()) {
                                    isPhoneFieldValid = false
                                }
                                if (isPhoneFieldValid) {
                                    enteredPhoneNumber = phone
                                    phoneLoginViewModel.onGetPhoneOtpButtonClicked(
                                        phone,
                                        rootNavController
                                    )
                                }
                            },
                            isLoading = false
                        )
                    }

                    UiState.Loading -> {
                        AuthScreenButton(
                            text = "Send OTP",
                            onClick = {
                                if (phone.isBlank()) {
                                    isPhoneFieldValid = false
                                }
                                if (isPhoneFieldValid) {
                                    enteredPhoneNumber = phone
                                    phoneLoginViewModel.onGetPhoneOtpButtonClicked(
                                        phone,
                                        rootNavController
                                    )
                                }
                            },
                            isLoading = true
                        )
                    }

                    is UiState.Success -> {
                    }
                }
            }
        }
    }
}
