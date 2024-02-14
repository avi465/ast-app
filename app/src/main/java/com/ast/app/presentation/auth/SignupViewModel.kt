package com.ast.app.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ast.app.data.signupUser
import com.ast.app.graphs.Graph
import com.ast.app.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onSignupButtonClicked(
        name: String,
        email: String,
        password: String,
        navController: NavController
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = signupUser(name, email, password)
                if (response != null) {
                    _uiState.value = UiState.Success(response)
                    Log.d("SignupViewModel", response.toString())
                    // Handle successful login (e.g., store token, navigate to home screen)
                    navController.navigate(Graph.MAIN_SCREEN_PAGE) {
                        popUpTo(Graph.AUTHENTICATION) {
                            inclusive = true
                        }
                    }
                } else {
                    _uiState.value = UiState.Error("Signup failed")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Something went wrong")
            }
        }
    }
}