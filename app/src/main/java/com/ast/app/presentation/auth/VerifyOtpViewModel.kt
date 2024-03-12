package com.ast.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ast.app.data.verifyPhoneOtp
import com.ast.app.graphs.Graph
import com.ast.app.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerifyOtpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onVerifyPhoneOtpButtonClicked(phone: String, otp: String, navController: NavController) {
        val countryCode = "+91"
        val phoneWithCountryCode = countryCode + phone
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = verifyPhoneOtp(phoneWithCountryCode, otp)
                if (response != null) {
                    _uiState.value = UiState.Success(response)
                    // Handle successful login (e.g., store token, navigate to home screen)
                    navController.navigate(Graph.MAIN_SCREEN_PAGE) {
                        popUpTo(Graph.AUTHENTICATION) {
                            inclusive = true
                        }
                    }
                } else {
                    _uiState.value = UiState.Error("Can't verify otp")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Something went wrong")
            }
        }
    }
}