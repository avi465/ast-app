package com.ast.app.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.ast.app.R
import com.ast.app.presentation.common.PrivacyPolicy

@Composable
fun SignupScreen(
) {
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_l)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            label = {
                Text(
                    text = "Enter your full name",
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            value = name,
            onValueChange = { name = it },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            label = {
                Text(
                    text = "Enter e-mail address",
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
                    text = "Enter strong password",
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

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.button_height))
        ) {
            Text(text = "Sign up", style = MaterialTheme.typography.titleMedium)
        }

        //privacy policy component
        PrivacyPolicy()
    }
}