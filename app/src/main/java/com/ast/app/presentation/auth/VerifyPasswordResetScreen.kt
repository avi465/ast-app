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
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ast.app.R
import com.ast.app.presentation.auth.onboarding.OnboardingScreen
import com.ast.app.navigation.OnBoardTopAppBar

@Composable
fun VerifyPasswordResetScreen(
    navController: NavController
) {
    var otp by rememberSaveable {
        mutableStateOf("")
    }
    val otpSize = 6

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OnBoardTopAppBar(
                currentScreenTitle = OnboardingScreen.VerifyPasswordReset.title,
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Enter 6-digit OTP",
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    value = otp,
                    onValueChange = {
                        if (it.length <= otpSize) otp = it
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Key, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.button_height))
                ) {
                    Text(text = "Verify", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}