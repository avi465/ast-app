package com.ast.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ast.app.data.getPhoneOtp
import com.ast.app.graphs.AuthScreen
import com.ast.app.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhoneLoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onGetPhoneOtpButtonClicked(phone: String, navController: NavController) {
        val countryCode = "+91"
        val phoneWithCountryCode = countryCode + phone
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = getPhoneOtp(phoneWithCountryCode)
                if (response != null) {
                    _uiState.value = UiState.Success(response)
                    // Handle successful login (e.g., store token, navigate to home screen)
                    navController.navigate(AuthScreen.VerifyOtp.route){
                        _uiState.value = UiState.Initial
                    }
                } else {
                    _uiState.value = UiState.Error("Can't send otp")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Something went wrong")
            }
        }
    }
}