package com.ast.app.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ast.app.MainActivity
import com.ast.app.data.loginUser
import com.ast.app.graphs.Graph
import com.ast.app.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmailLoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onLoginButtonClicked(
        username: String,
        password: String,
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = loginUser(username, password)
                if (response.success && response.data != null) {
                    _uiState.value = UiState.Success(response)
                    // Handle successful login (e.g., store token, navigate to home screen)
                    navController.navigate(Graph.MAIN_SCREEN_PAGE) {
                        popUpTo(Graph.MAIN_SCREEN_PAGE) {
                            inclusive = true
                        }
                    }
                    // Remember login using shared prefs
                    storeCredentials(username, password, context)
                    // todo: do manage login state in app
                } else if (!response.success && response.errors != null) {
                    _uiState.value = UiState.Error(response.message)
                } else {
                    _uiState.value = UiState.Error("Something went wrong")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: $e")
            }
        }
    }

    private fun storeCredentials(username: String, password: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("password", password)
            putBoolean("remember_me", true)
            apply()
        }
    }
}