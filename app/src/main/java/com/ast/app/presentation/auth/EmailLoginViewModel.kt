package com.ast.app.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.loginUser
import com.ast.app.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmailLoginViewModel : ViewModel() {
    sealed class LoginState {
        data object Initial : LoginState()
        data object Loading : LoginState()
        data class Success(val response: LoginResponse) : LoginState()
        data class Error(val error: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState

    fun onLoginButtonClicked(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = loginUser(username, password)
                if (response != null) {
                    _loginState.value = LoginState.Success(response)
                    // Handle successful login (e.g., store token, navigate to home screen)
                    Log.d("Login", "Email Login Successful")
                } else {
                    _loginState.value = LoginState.Error("Login failed")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("An error occurred")
            }
        }
    }
}