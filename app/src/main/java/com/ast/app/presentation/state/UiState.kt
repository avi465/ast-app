package com.ast.app.presentation.state

import com.ast.app.model.LoginResponse
import com.ast.app.model.SignupResponse

sealed class UiState {
    data object Initial : UiState()
    data object Loading : UiState()
//    data class Success(val response: LoginResponse?) : UiState()
    data class Success(val response: Any?) : UiState()
    data class Error(val error: String) : UiState()
}