package com.ast.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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

    fun onLoginButtonClicked(username: String, password: String, navController: NavController) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = loginUser(username, password)
                if (response != null) {
                    _uiState.value = UiState.Success(response)
                    // Handle successful login (e.g., store token, navigate to home screen)
                    navController.navigate(Graph.MAIN_SCREEN_PAGE){
                        popUpTo(Graph.AUTHENTICATION){
                            inclusive = true
                        }
                    }
                } else {
                    _uiState.value = UiState.Error("Login failed")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Something went wrong")
            }
        }
    }
}