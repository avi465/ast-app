package com.ast.app.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ast.app.R
import com.ast.app.graphs.AuthScreen
import com.ast.app.navigation.OnBoardTopAppBar
import com.ast.app.presentation.common.AuthScreenButton
import com.ast.app.presentation.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VerifyOtpScreen(
    verifyOtpViewModel: VerifyOtpViewModel = viewModel(),
    navController: NavController
) {
    val uiState by verifyOtpViewModel.uiState.collectAsState()

    var otp by rememberSaveable {
        mutableStateOf("")
    }
    var isOtpFieldValid by rememberSaveable {
        mutableStateOf(true)
    }
    val phone = enteredPhoneNumber

    val otpSize = 6

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OnBoardTopAppBar(
                currentScreenTitle = AuthScreen.VerifyOtp.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                            text = "Enter 6-digit OTP*",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    value = otp,
                    onValueChange = {
                        if (it.length <= otpSize) {
                            otp = it
                            isOtpFieldValid = otp.isNotBlank()
                        }
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Key, contentDescription = null)
                    },
                    trailingIcon = {
                        if (!isOtpFieldValid) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = "error"
                            )
                        } else if (otp.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    otp = ""
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = "cancel",
                                )
                            }
                        }
                    },
                    isError = !isOtpFieldValid,
                    supportingText = {
                        if (!isOtpFieldValid) {
                            Text(
                                text = "Otp can't be blank",
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Text(text = "*required")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                ResendOtpCountdown(
                    phone = phone,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    scope = scope
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
                            text = "Verify",
                            onClick = {
                                if (otp.isBlank()) {
                                    isOtpFieldValid = false
                                }
                                if (isOtpFieldValid) {
                                    verifyOtpViewModel.onVerifyPhoneOtpButtonClicked(
                                        phone,
                                        otp,
                                        navController
                                    )
                                }
                            },
                            isLoading = false
                        )
                    }

                    UiState.Initial -> {
                        AuthScreenButton(
                            text = "Verify",
                            onClick = {
                                if (otp.isBlank()) {
                                    isOtpFieldValid = false
                                }
                                if (isOtpFieldValid) {
                                    verifyOtpViewModel.onVerifyPhoneOtpButtonClicked(
                                        phone,
                                        otp,
                                        navController
                                    )
                                }
                            },
                            isLoading = false
                        )
                    }

                    UiState.Loading -> {
                        AuthScreenButton(
                            text = "Verify",
                            onClick = {
                                if (otp.isBlank()) {
                                    isOtpFieldValid = false
                                }
                                if (isOtpFieldValid) {
                                    verifyOtpViewModel.onVerifyPhoneOtpButtonClicked(
                                        phone,
                                        otp,
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
            }
        }
    }
}

/*TODO: navigate up chaining(bug)*/
@Composable
fun ResendOtpCountdown(
    phone: String,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    phoneLoginViewModel: PhoneLoginViewModel = viewModel()
) {
    val uiState by phoneLoginViewModel.uiState.collectAsState()

    var time by rememberSaveable { mutableIntStateOf(59) }
    var isCountingDown by rememberSaveable { mutableStateOf(true) }


    val resendOtpText = buildAnnotatedString {
        if (time > 0) {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                append("Resend OTP in ")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {

                append("$time Sec")
            }
        } else {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Resend OTP")
            }
        }
    }

    LaunchedEffect(isCountingDown) {
        if (isCountingDown) {
            while (time > 0) {
                delay(1000)
                time--
            }
            isCountingDown = false
        }
    }

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

            ResendOtpButton(
                phoneLoginViewModel = phoneLoginViewModel,
                navController = navController,
                resendOtpText = resendOtpText,
                time = time,
                isLoading = false
            )
        }

        UiState.Initial -> {
            ResendOtpButton(
                phoneLoginViewModel = phoneLoginViewModel,
                navController = navController,
                resendOtpText = resendOtpText,
                time = time,
                isLoading = false
            )
        }

        UiState.Loading -> {
            ResendOtpButton(
                phoneLoginViewModel = phoneLoginViewModel,
                navController = navController,
                resendOtpText = resendOtpText,
                time = time,
                isLoading = true
            )
        }

        is UiState.Success -> {
        }
    }

}

@Composable
fun ResendOtpButton(
    phoneLoginViewModel: PhoneLoginViewModel,
    navController: NavController,
    resendOtpText: AnnotatedString,
    time: Int,
    isLoading: Boolean,
) {
    val phone = enteredPhoneNumber

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!isLoading) {
            ClickableText(
                text = resendOtpText,
                style = MaterialTheme.typography.labelLarge,
                onClick = {
                    if (time == 0) {
                        // this implementation creation issue with recalling of itself i.e loopback in navigation
                        phoneLoginViewModel.onGetPhoneOtpButtonClicked(
                            phone,
                            navController
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.Start)
                    .drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height
                        drawLine(
                            color = Color.Gray,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset),
                            pathEffect = pathEffect
                        )
                    },
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = LocalContentColor.current,
                strokeWidth = 2f.dp,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}