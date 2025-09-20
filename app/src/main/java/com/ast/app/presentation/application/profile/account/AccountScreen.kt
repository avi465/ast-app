package com.ast.app.presentation.application.profile.account

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ast.app.model.UserModel
import com.ast.app.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavHostController,
    accountViewModel: AccountViewModel = viewModel()
) {
    val accountState by accountViewModel.accountState.collectAsState()
    val isRefreshing by accountViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            accountViewModel.fetchUserProfile()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (accountState) {
                is UiState.Success -> {
                    val user = (accountState as UiState.Success<UserModel>).data
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        ProfileScreen(user = user, accountViewModel = accountViewModel)
                    }
                }

                is UiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = (accountState as UiState.Error).error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        TextButton(onClick = {
                            accountViewModel.fetchUserProfile()
                        }) {
                            Text(text = "Reload")
                        }
                    }
                }

                is UiState.Loading -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(user: UserModel, accountViewModel: AccountViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var fieldToEdit by remember { mutableStateOf("") }
    var fieldValue by remember { mutableStateOf("") }

    val context = LocalContext.current

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            shape = RectangleShape,
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Edit $fieldToEdit", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                var newValue by remember { mutableStateOf(fieldValue) }

                OutlinedTextField(
                    value = newValue,
                    singleLine = true,
                    onValueChange = { newValue = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
//                        Toast.makeText(
//                            context,
//                            "$fieldToEdit updated to $newValue",
//                            Toast.LENGTH_SHORT
//                        ).show()

                        // handle value update here
                        UserModel(
                            id = user.id,
                            name = if (fieldToEdit == "Name") newValue else user.name,
                            email = if (fieldToEdit == "Email") newValue else user.email,
                            phone = if (fieldToEdit == "Phone Number") newValue else user.phone
                        ).also { updatedUser ->
                            accountViewModel.putUserProfile(updatedUser)
                        }

                        fieldValue = newValue
                        fieldToEdit = ""
                        showSheet = false
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save")
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        user.name?.let {
            ProfileItem(
                icon = Icons.Filled.AccountCircle,
                label = "Name",
                value = it,
                onClick = {
                    fieldToEdit = "Name"
                    fieldValue = user.name
                    showSheet = true
                }
            )
        }

        ProfileItem(
            icon = Icons.Default.Email,
            label = "Email",
            value = user.email,
            onClick = {
                fieldToEdit = "Email"
                fieldValue = user.email
                showSheet = true
            }
        )

        user.phone?.let {
            ProfileItem(
                icon = Icons.Default.Phone,
                label = "Phone Number",
                value = it,
                onClick = {
                    fieldToEdit = "Phone Number"
                    fieldValue = user.phone
                    showSheet = true
                }
            )
        }
    }
}


@Composable
fun ProfileItem(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { onClick() }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            leadingIcon = { Icon(imageVector = icon, contentDescription = label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Edit"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledLabelColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledLeadingIconColor = MaterialTheme.colorScheme.primary,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            enabled = false
        )
    }
}